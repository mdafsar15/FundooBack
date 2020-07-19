package com.bridgelabz.fundoo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//import org.omg.CORBA.PRIVATE_MEMBER;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "note")
@Data
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private long n_id;
	private String title;
	private String description;
	private boolean isArchived;
	private boolean isPinned;
	private boolean isTrashed;
	private String color;
	
	@CreationTimestamp
	@Column(name = "createdDate")
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	@Column(name = "updatedDate")
	private LocalDateTime updatedDate;
	private LocalDateTime reminderDate;
	

	@ManyToMany
	@JoinTable(name = "note_label", joinColumns = { @JoinColumn(name = "n_id") }, inverseJoinColumns = {
			@JoinColumn(name = "l_Id") })
	@JsonIgnore
	private List<Label> labelsList;

	@JsonIgnore
	@ManyToMany(mappedBy = "collaboratedNotes")
	private List<User> collaboratedUsers;

}