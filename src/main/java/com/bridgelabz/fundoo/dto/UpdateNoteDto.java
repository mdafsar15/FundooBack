package com.bridgelabz.fundoo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UpdateNoteDto {

	private int n_id;
	private String title;
	private String description;
	private boolean isArchived;
	private boolean isTrashed;
	private boolean isPinned;
	private LocalDateTime updatedtime;

}
