package com.bridgelabz.fundooNotes.elasticSearch;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.bridgelabz.fundooNotes.note.model.Note;
import com.bridgelabz.fundooNotes.note.model.NoteEs;
import com.bridgelabz.fundooNotes.user.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService
{
	String INDEX="notedetails";
	String TYPE="note";
	
	private RestHighLevelClient client;

    private ObjectMapper objectMapper;

	public String createNote(@RequestBody NoteEs note) throws IOException
	{
		 UUID uuid = UUID.randomUUID();
		 
		 note.setNoteId(uuid.toString());

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
	
	
	
}
