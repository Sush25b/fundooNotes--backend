package com.bridgelabz.fundooNotes.note.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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

@PropertySource("classpath:message.properties")
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
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException(environment.getProperty("user.search")));

		note.setCreatedTime(currDateTime);
		note.setLastUpdateTime(currDateTime);
		//top get all the notes-->of user
		user.getNote().add(note);
		//save user
		userRepository.save(user);

		return ResponseSender.sendUserRespone("Note is created", 200);
	}

	//trash
	public ResponseEntity<UserResponse> trashNote(Long noteId, String token) 
	{
		//the note is not going to DELETE 
		//in Note===>table the (column--->trash is going to be set as TRUE/False)
		System.out.println(noteId+" "+token);
		Long userid = TokenUtil.decodeToken(token);

		//get particular user--->from database
		User user = userRepository.findById(userid).orElseThrow(() -> new UserException(environment.getProperty("user.search")));

		//get all notes-->of that user
		List<Note> notes = user.getNote();

		//from the Notes List-->filter to get particular note by noteid
		Note filteredNote = notes.stream().filter(data -> data.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new UserException(environment.getProperty("user.note")));

		//check particular note Pinned STATUS  
		if(filteredNote.isTrash()==true)
		{
			filteredNote.setTrash(false);
			filteredNote.setLastUpdateTime(currDateTime);    
			//add the changed note to-->User Notes List
			notes.add(filteredNote);
		}
		else
		{
			filteredNote.setTrash(true);
			filteredNote.setLastUpdateTime(currDateTime);    
			//add the changed note to-->User Notes List
			notes.add(filteredNote);
		}
		user.setNote(notes);
		userRepository.save(user);

		return ResponseSender.sendUserRespone("Note moved to trash",200);
	}

	//Read //get
	public List<Note> getUserNote(String token) 
	{
		Long userId = TokenUtil.decodeToken(token);
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("invalid user"));
		List<Note> userNote = user.getNote();

		return userNote;
	}


	//update
	@Override
	public ResponseEntity<UserResponse> updateNote(Long noteId,NoteDto noteDto, String token)
	{
		System.out.println(noteId+" "+noteDto+" "+token);
		Long userid = TokenUtil.decodeToken(token);

		//get particular user--->from database
		User user = userRepository.findById(userid).orElseThrow(() -> new UserException(environment.getProperty("user.note")));
		//get all notes-->of that user
		List<Note> notes = user.getNote();

		//from the Notes List-->we filter to get particular noteid
		Note filteredNote = notes.stream().filter(data -> data.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new UserException(environment.getProperty("user.note")));
		filteredNote.setTitle(noteDto.getTitle());
		filteredNote.setDescription(noteDto.getDescription());
		filteredNote.setLastUpdateTime(currDateTime);    
		//add the new note to-->User Notes List
		notes.add(filteredNote);

		user.setNote(notes);
		userRepository.save(user);

		return ResponseSender.sendUserRespone("note updated successfully", 200);
	}

	
	//ispinned
	@Override
	public ResponseEntity<UserResponse> isPinned(Long noteId, String token)
	{
		System.out.println(noteId+" "+token);
		Long userid = TokenUtil.decodeToken(token);

		//get particular user--->from database
		User user = userRepository.findById(userid).orElseThrow(() -> new UserException(environment.getProperty("user.note")));
		//get all notes-->of that user
		List<Note> notes = user.getNote();

		//from the Notes List-->we filter to get particular note by noteid
		Note filteredNote = notes.stream().filter(data -> data.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new UserException(environment.getProperty("user.note")));

		//check note Pinned STATUS  
		if(filteredNote.isPinned()==true)
		{
			filteredNote.setPinned(false);
			filteredNote.setLastUpdateTime(currDateTime);    
			//add the changed note to-->User Notes List
			notes.add(filteredNote);
		}
		else
		{	filteredNote.setPinned(true);
			filteredNote.setLastUpdateTime(currDateTime);    
			//add the changed note to-->User Notes List
			notes.add(filteredNote);
		}

		user.setNote(notes);
		userRepository.save(user);

		return ResponseSender.sendUserRespone("note updated successfully", 200);
	}


	//isArchieve
	@Override
	public ResponseEntity<UserResponse> isArchieve(Long noteId, String token)
	{
		System.out.println(noteId+" "+token);
		Long userid = TokenUtil.decodeToken(token);

		//get particular user--->from database
		User user = userRepository.findById(userid).orElseThrow(() -> new UserException(environment.getProperty("user.note")));

		//get all notes-->of that user
		List<Note> notes = user.getNote();

		//from the Notes List-->we filter to get particular note by noteid
		Note filteredNote = notes.stream().filter(data -> data.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new UserException(environment.getProperty("user.note")));

		//check note Archieve STATUS  
		if(filteredNote.getIsArchive()==true)
		{
			filteredNote.setIsArchive(false);
			filteredNote.setLastUpdateTime(currDateTime);    
			//add the changed note to-->User Notes List
			notes.add(filteredNote);
		}
		else
		{
			filteredNote.setIsArchive(true);
			filteredNote.setLastUpdateTime(currDateTime);   
			//add the changed note to-->User Notes List
			notes.add(filteredNote);
		}

		user.setNote(notes);
		userRepository.save(user);

		return ResponseSender.sendUserRespone("note updated successfully", 200);
	}

	
	//delete
	@Override
	public ResponseEntity<UserResponse> deleteNote(Long noteId, String token) 
	{
		System.out.println(noteId+" "+token);
		Long userid = TokenUtil.decodeToken(token);

		//get particular user--->from database
		User user = userRepository.findById(userid).orElseThrow(() -> new UserException(environment.getProperty("user.note")));
		//get all notes-->of that user
		List<Note> notes = user.getNote();

		//for loop to search note
		for(Note n:notes)
		{
			if(n.getNoteId()==noteId )
			{
				notes.remove(noteId);
				System.out.println(notes);
				System.out.println("note:"+" "+noteId+" "+"successfully delted");
			}
			else
				System.out.println("note:"+" "+noteId+" "+" not successfully delted");
		}

		//				Optional<Note> notePresent=noteRepository.findById(noteId);
		//				
		//				if (notePresent.isPresent())
		//				{
		//					notes.remove(noteId);
		//					System.out.println("note:"+" "+noteId+" "+"successfully delted");
		//				}
		//				else
		//					System.out.println("note:"+" "+noteId+" "+" not successfully delted");

		System.out.println(notes);
		user.setNote(notes);
		userRepository.save(user);

		return null;
	}

}





