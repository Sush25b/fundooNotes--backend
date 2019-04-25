package com.bridgelabz.fundooNotes.elasticSearch;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.bridgelabz.fundooNotes.note.model.NoteEs;
import com.bridgelabz.fundooNotes.user.response.UserResponse;

public interface ElasticSearchService 
{
	public String createNote(NoteEs note)  throws IOException;

	public NoteEs findById(String id) throws IOException;
}
