package com.bridgelabz.fundooNotes.elasticSearch;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.bridgelabz.fundooNotes.note.model.Note;
import com.bridgelabz.fundooNotes.note.model.NoteEs;
import com.bridgelabz.fundooNotes.user.response.UserResponse;

public interface ElasticSearchService 
{
	public String createNote(Note note)  throws IOException;

	public NoteEs findById(String id) throws IOException;

	public List<NoteEs> findAll() throws IOException;

	public String deleteNote(String id) throws IOException;

	List<Note> getNoteByAllFeilds(String searchName, Long userid) throws Exception;
	
}
