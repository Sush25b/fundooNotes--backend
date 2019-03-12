package com.bridgelabz.fundooNotes.note.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooNotes.note.dto.NoteDto;
import com.bridgelabz.fundooNotes.note.model.Note;
import com.bridgelabz.fundooNotes.note.service.NoteServices;
import com.bridgelabz.fundooNotes.user.dto.LoginDto;
import com.bridgelabz.fundooNotes.user.dto.UserDto;
import com.bridgelabz.fundooNotes.user.response.UserResponse;
import com.bridgelabz.fundooNotes.user.service.UserServicesImpl;

@RestController
@RequestMapping("/fundooNotes/note")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*",exposedHeaders={"jwtTokens"})
public class NoteRestController 
{
	@Autowired(required=true) 
	NoteServices noteServices;

	//	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	//successfully
	/**
	 * Method to Create a note 
	 */
	@PostMapping("/createnote") 
	public ResponseEntity<UserResponse> createNote(@RequestBody NoteDto noteDto,@RequestHeader String token) // throws UnsupportedEncodingException
	{ 		
		System.out.println("a");

		return noteServices.createNote(noteDto, token); 
	}

	//successfull
	/**
	 * Method to move a-->particular note to trash 
	 */
	@PostMapping(value="/trash") 
	public ResponseEntity<UserResponse> trashNote(@RequestParam Long noteId,@RequestHeader String token) 
	{
		System.out.println("***************");
		return noteServices.trashNote(noteId, token);
	}

	//successfully
	/**
	 * Method to getAll notes--->from particular id 
	 */
	@GetMapping(value="/getAll") 
	public List<Note> getAllNote(@RequestHeader String token) throws UnsupportedEncodingException
	{
		return noteServices.getUserNote(token);
	}

	//successfully
	/**
	 * Method to update a--->particular note 
	 */
	@PostMapping("/updatenote") 
	public ResponseEntity<UserResponse> updateNote(@RequestParam("noteId") Long noteId,@RequestBody NoteDto noteDto,@RequestHeader String token) // UserException2 
	{ 
		return noteServices.updateNote(noteId, noteDto,token);
	}

	/**
	 * Method to pin/unpin a--->particular note 
	 */
	@PostMapping("/ispinned") 
	public ResponseEntity<UserResponse> isPinned(@RequestParam("noteId") Long noteId,@RequestHeader String token) 
	{ 
		return noteServices.isPinned(noteId,token);
	}

	/**
	 * Method to update a--->particular note 
	 */
	@PostMapping("/isarchieve") 
	public ResponseEntity<UserResponse> isArchieve(@RequestParam("noteId") Long noteId,@RequestHeader String token) // UserException2 
	{ 
		return noteServices.isArchieve(noteId, token);
	}

	//successfull
	/**
	 * Method to move a-->particular note to trash 
	 */
	@PostMapping(value="/delete") 
	public ResponseEntity<UserResponse> deleteNote(@RequestParam Long noteId,@RequestHeader String token) 
	{
		System.out.println("***************");
		return noteServices.deleteNote(noteId, token);
	}
}
