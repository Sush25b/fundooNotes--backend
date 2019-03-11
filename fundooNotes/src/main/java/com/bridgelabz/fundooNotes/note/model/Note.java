package com.bridgelabz.fundooNotes.note.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Note")
public class Note
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="note_id")
	private Long noteId;
	
	@Column(name ="title" )
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "colour")
	private String colour;
	
	@Column(name = "is_pinned")
	private boolean isPinned;
	
	@Column(name = "is_trash")
	private boolean isTrash;
	
	@Column(name= "is_archive")
	private boolean isArchive;
	
	@Column(name = "reminder")
	private LocalDateTime reminder;
	
	@Column(name = "created_time")
	private LocalDateTime createdTime;
	
	@Column(name = "lastupdate_time")
	private LocalDateTime lastUpdateTime;

	
	public Long getNoteId() {
		return noteId;
	}


	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}


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


	public String getColour() {
		return colour;
	}


	public void setColour(String colour) {
		this.colour = colour;
	}


	public boolean isPinned() {
		return isPinned;
	}


	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}


	public boolean isTrash() {
		return isTrash;
	}


	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}


	public Boolean getIsArchive() {
		return isArchive;
	}


	public void setIsArchive(Boolean isArchive) {
		this.isArchive = isArchive;
	}


	public LocalDateTime getReminder() {
		return reminder;
	}


	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}


	public LocalDateTime getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}


	public LocalDateTime getLastUpdateTime() {
		return lastUpdateTime;
	}


	public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}


	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", title=" + title + ", description=" + description + ", colour=" + colour
				+ ", isPinned=" + isPinned + ", isTrash=" + isTrash + ", isArchive=" + isArchive + ", reminder="
				+ reminder + ", createdTime=" + createdTime + ", lastUpdateTime=" + lastUpdateTime + "]";
	}

	

}
