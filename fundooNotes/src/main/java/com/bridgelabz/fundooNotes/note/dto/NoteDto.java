package com.bridgelabz.fundooNotes.note.dto;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class NoteDto 
{
	private String title;

	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "NoteDto [title=" + title + ", description=" + description + "]";
	}
	
	
	
}
