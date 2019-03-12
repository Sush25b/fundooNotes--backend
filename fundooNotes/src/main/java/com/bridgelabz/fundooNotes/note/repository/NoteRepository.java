package com.bridgelabz.fundooNotes.note.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundooNotes.note.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> 
{
	public Note findByNoteId(Long noteid);
}
