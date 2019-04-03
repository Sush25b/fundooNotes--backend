package com.bridgelabz.fundooNotes.note.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bridgelabz.fundooNotes.note.dto.NoteDto;
import com.bridgelabz.fundooNotes.note.model.Note;
import com.bridgelabz.fundooNotes.user.exception.UserException;
import com.bridgelabz.fundooNotes.user.response.UserResponse;

public interface NoteServices 
{
	public ResponseEntity<UserResponse> createNote(NoteDto noteDto, String Token);

	//
	public ResponseEntity<UserResponse> updateNote(Long noteId, NoteDto noteDto, String token);
	
	public ResponseEntity<UserResponse> trashNote(Long noteId, String token);

	public List<Note> getUserNote(String token);

	public ResponseEntity<UserResponse> deleteNote(Long noteId, String token);
	
	public ResponseEntity<UserResponse> isPinned(Long noteId, String token);

	public ResponseEntity<UserResponse> isArchieve(Long noteId, String token);

	public List<Note> getUserNotes(String token, boolean trash, boolean archive);
	
}
