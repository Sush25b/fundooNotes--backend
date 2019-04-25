package com.bridgelabz.fundooNotes.elasticSearch;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooNotes.note.model.Note;
import com.bridgelabz.fundooNotes.note.model.NoteEs;
import com.bridgelabz.fundooNotes.user.response.ResponseSender;
import com.bridgelabz.fundooNotes.user.response.UserResponse;

@RestController("/es")
public class ESController 
{
	@Autowired(required=true)
	ElasticSearchService esService;
	
	@PostMapping(value="/createnote") 
	public String createNote(@RequestBody NoteEs note)  throws IOException
	{
		return esService.createNote(note);
		//return ResponseSender.sendUserResponse("note unpinned successfully", 200);
	}
	
	 @GetMapping("/{id}")
    public NoteEs findById(@PathVariable String id) throws Exception {

        return esService.findById(id);
    }
	
	
	
}