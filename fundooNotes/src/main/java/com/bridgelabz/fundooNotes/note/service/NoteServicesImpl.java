package com.bridgelabz.fundooNotes.note.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooNotes.note.dto.NoteDto;
import com.bridgelabz.fundooNotes.note.model.Note;
import com.bridgelabz.fundooNotes.note.repository.NoteRepository;
import com.bridgelabz.fundooNotes.user.exception.UserException;
import com.bridgelabz.fundooNotes.user.model.User;
import com.bridgelabz.fundooNotes.user.repository.IUserRepository;
import com.bridgelabz.fundooNotes.user.response.ResponseSender;
import com.bridgelabz.fundooNotes.user.response.UserResponse;
import com.bridgelabz.fundooNotes.utility.TokenUtil;

@Service
public class NoteServicesImpl implements NoteServices
{

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	Environment environment;
	
	@Autowired
	NoteRepository noteRepository;
	
	/**
	 * to get current date and time
	 */
	LocalDateTime currDateTime = LocalDateTime.now();

	//Create
	public ResponseEntity<UserResponse> createNote(NoteDto noteDto, String token)
	{
		Note note = modelMapper.map(noteDto, Note.class);
		
		Long userId = TokenUtil.decodeToken(token);
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException(404, "user id not found"));
		
		note.setCreatedTime(currDateTime);
		note.setLastUpdateTime(currDateTime);
		
		//getNote() method--->is their in User POJO Class to get List<Note> 
			//and after getting all Notes, we store New created note--->to the List
		user.getNote().add(note);
		userRepository.save(user);
		
		return ResponseSender.sendUserRespone("Note is created", 200);
	}

	//update
	@Override
	public ResponseEntity<UserResponse> updateNote(Long noteId,NoteDto noteDto, String token)
	{
		System.out.println(noteId+" "+token);
		Long userid = TokenUtil.decodeToken(token);
		
		User user = userRepository.findById(userid)
				.orElseThrow(() -> new UserException(environment.getProperty("user.note")));
		
		List<Note> notes = user.getNote();
		
		Note note = noteRepository.findByNoteId(noteId);
		Note filteredNote = notes.stream().filter(data -> data.getNoteId().equals(noteId)).findFirst().orElse(null);
		filteredNote.setTitle(noteDto.getTitle());
		filteredNote.setDescription(noteDto.getDescription());
		filteredNote.setLastUpdateTime(currDateTime);    //(LocalDateTime.now());
		
		notes.add(filteredNote);
		user.setNote(notes);
		userRepository.save(user);
		return ResponseSender.sendUserRespone("note updated successfully", 200);
	}
	
	//Delete
	public ResponseEntity<UserResponse> trashNote(Long noteId, String token) 
	{
		System.out.println(noteId+"   "+token);
		Note note = noteRepository.findByNoteId(noteId);
	
		//the note is not going to DELETE 
			//in Note===>table the (column--->trash is going to be set as TRUE)
		note.setTrash(true);
		noteRepository.save(note);
	
		return ResponseSender.sendUserRespone("Note moved to trash",200);
	}

	//Read
	public List<Note> getUserNote(String token) 
	{
		Long userId = TokenUtil.decodeToken(token);
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("invalid user"));
		List<Note> userNote = user.getNote();
		
		return userNote;
	}
	
}






