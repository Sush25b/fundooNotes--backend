package com.bridgelabz.fundooNotes.elasticSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.bridgelabz.fundooNotes.note.model.Note;
import com.bridgelabz.fundooNotes.note.model.NoteEs;
import com.bridgelabz.fundooNotes.user.response.UserResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService
{
//	String INDEX="notedetails";
//	String TYPE="note";
	
	String INDEX="es";
	String TYPE="createnote";
	
	@Autowired
	private RestHighLevelClient client;

	@Autowired
    private ObjectMapper objectMapper;

	public String createNote(NoteEs note) throws IOException
	{
//		 UUID uuid = UUID.randomUUID();
//		 
//		 note.setNoteId(uuid.toString());

	        Map<String, Object> documentMapper = objectMapper.convertValue(note, Map.class);

	        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, note.getNoteId()).source(documentMapper);

	        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

	        return indexResponse.getResult().name();
	}

	@Override
	public NoteEs findById(String id) throws IOException
	{
		GetRequest getRequest = new GetRequest(INDEX, TYPE, id);

        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        
        Map<String, Object> resultMap = getResponse.getSource();

        return objectMapper.convertValue(resultMap, NoteEs.class);

	}

	@Override
	public List<NoteEs> findAll() throws IOException 
	{
		 SearchRequest searchRequest = new SearchRequest();
	        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
	        searchRequest.source(searchSourceBuilder);

	        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

	        return getSearchResult(searchResponse);
	}
	
	private List<NoteEs> getSearchResult(SearchResponse response)
	{
        SearchHit[] searchHit = response.getHits().getHits();
        List<NoteEs> profileDocuments = new ArrayList<>();

        if (searchHit.length > 0) 
        {
            Arrays.stream(searchHit).forEach(hit -> profileDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(),NoteEs.class)) );
        }

        return profileDocuments;
    }

//***********************************************************************************
	@Override
	public String deleteNote(String id) throws IOException
	{
		 DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
	     DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);

	     return response.getResult().name();
	}
	
	@Override
	public List<Note> getNoteByAllFeilds(String searchName,Long userid) throws Exception 
	{
		System.out.println(searchName+" "+userid);
		try {		
			SearchRequest searchRequest = new SearchRequest("es");
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

			QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery("*"+searchName+"*")
											.analyzeWildcard(true).field("title", 3.0f).field("description",2.0f));
											//.filter(QueryBuilders.termQuery("esuserid", String.valueOf(userid)));
			
			searchSourceBuilder.query(queryBuilder);
			searchRequest.source(searchSourceBuilder);
//			SearchRequest searchRequest = new SearchRequest("es","createnote").source(searchSourceBuilder);

			System.out.println(searchRequest);
			System.out.println("1");
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			System.out.println("2");

			SearchHit[] searchedNotes = searchResponse.getHits().getHits();

			
			List<Note> ListOfNotes = new ArrayList<Note>();

			if (searchedNotes.length > 0) {
				Arrays.stream(searchedNotes).forEach(note -> {
					try {
						ListOfNotes.add(objectMapper.readValue(note.getSourceAsString(), Note.class));
					} catch (JsonParseException e) {

						e.printStackTrace();
					} catch (JsonMappingException e) {

						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
			return ListOfNotes;
		} 
		catch (IOException e) 
		{
			throw new Exception("Internal Server error");
		}
	
	}

	@Override
	public String createNote(Note note) throws IOException 
	{
//		UUID uuid = UUID.randomUUID();
//			 note.setNoteId((long) 111);
		
		 Map<String, Object> documentMapper = objectMapper.convertValue(note, Map.class);
		 
	        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE,String.valueOf(note.getNoteId()) ).source(documentMapper);

	        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
	        System.out.println("///////////////////");
	        return indexResponse.getResult().name();
	}

}
