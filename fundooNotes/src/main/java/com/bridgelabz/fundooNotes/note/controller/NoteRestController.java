package com.bridgelabz.fundooNotes.note.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*",exposedHeaders="jwtToken")
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
	public ResponseEntity<UserResponse> createNote(@RequestBody NoteDto noteDto,@RequestHeader(value="jwtToken") String jwtToken) // throws UnsupportedEncodingException
	{ 		
		System.out.println(noteDto);
		return noteServices.createNote(noteDto, jwtToken); 
	}

	//successfully
	/**
	 * Method to getAll notes--->from particular id 
	 */
	@GetMapping(value="/getAll") 
	public List<Note> getAllNote(@RequestHeader(value="jwtToken") String token) throws UnsupportedEncodingException
	{
		System.out.println("aaaaaaaaa");
		return noteServices.getUserNote(token);
	}

	//successfully
	/**
	 * Method to getAll notes--->from particular id 
	 */
	@GetMapping(value="/getAlls") 
	public List<Note> getAllNotes(@RequestParam boolean trash,@RequestParam boolean archive,@RequestHeader(value="jwtToken") String token) throws UnsupportedEncodingException
	{
		System.out.println("======");
		List<Note> notes=noteServices.getUserNotes(token,trash,archive);
		System.out.println("notes-->"+notes);
		return notes;
	}
	
	//successfully
	/**
	 * Method to update a--->particular note 
	 */
	@PutMapping(value="/updatenote") 
	public ResponseEntity<UserResponse> updateNote(@RequestParam("noteId") Long noteId,@RequestBody NoteDto noteDto,@RequestHeader(value="jwtToken") String token) // UserException2 
	{ 
		return noteServices.updateNote(noteId, noteDto,token);
	}

	/**
	 * Method to pin/unpin a--->particular note 
	 */
	//@PostMapping("/ispinned") 
	//public ResponseEntity<UserResponse> isPinned(@RequestBody Long noteId,@RequestHeader(value="jwtToken") String jwtToken) 
    @PutMapping("/ispinned")
	public ResponseEntity<UserResponse> isPinned(@RequestParam("noteId") Long noteId,@RequestHeader(value="jwtToken") String jwtToken)
	{ 
		System.out.println(noteId+"   "+jwtToken+"QQQQQQQQQQQQQQQQQQQQ");
		return noteServices.isPinned(noteId,jwtToken);
	}

	/**
	 * Method to update a--->particular note 
	 */
    @PutMapping("/isarchieve") 
	public ResponseEntity<UserResponse> isArchieve(@RequestParam("noteId") Long noteId,@RequestHeader(value="jwtToken") String jwtToken)
	//public ResponseEntity<UserResponse> isArchieve(@RequestParam("noteId") Long noteId,@RequestHeader String token) // UserException2 
	{ 
		System.out.println(noteId+"   "+jwtToken+"QQQQQQQQQQQQQQQQQQQQ");
		return noteServices.isArchieve(noteId, jwtToken);
	}
	
	//successfull
	/**
	 * Method to move a-->particular note to trash 
	 */
    @PutMapping(value="/trash") 
	public ResponseEntity<UserResponse> trashNote(@RequestParam("noteId") Long noteId,@RequestHeader(value="jwtToken") String jwtToken) 
	{
		System.out.println("***************");
		return noteServices.trashNote(noteId, jwtToken);
	}

	//successfull
	/**
	 * Method to delete note
	 */
	@PostMapping(path="/delete") 
	public ResponseEntity<UserResponse> deleteNote(@RequestParam("noteId") Long noteId,@RequestHeader(value="jwtToken") String jwtToken) 
	{
		System.out.println("***************");
		return noteServices.deleteReminder(noteId, jwtToken);
	}
	
	//successfull
	/**
	 * To add reminder to a particular note 
	 */
	@PostMapping(path="/reminder") 
	public ResponseEntity<UserResponse> addReminder(@RequestParam("noteId") Long noteid,@RequestParam("reminder") String reminder,@RequestHeader(value="jwtToken") String jwtToken)
	{
		System.out.println("3333");
		return noteServices.addReminder(noteid,reminder,jwtToken);
	}
	
	@DeleteMapping(path="/reminderdelete") 
	public ResponseEntity<UserResponse> deleteReminder(@RequestParam("noteId") Long noteid,@RequestHeader(value="jwtToken") String jwtToken) 
	{
		System.out.println("33333333333333");
		return noteServices.deleteReminder(noteid, jwtToken);
	}
  //successfull
  	/**
  	 * Method to move a-->particular note to trash 
  	 */
//    @PutMapping(value="/addReminder") 
//  	public ResponseEntity<UserResponse> addreminder(@RequestParam("noteId") Long noteId,@RequestHeader(value="jwtToken") String jwtToken) 
//  	{
//  		System.out.println("***************");
//  		return noteServices.addreminder(noteId, jwtToken);
//  	}
}
