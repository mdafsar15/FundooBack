package com.bridgelabz.fundoo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//import com.bridzelabz.fundoonotes.model.NotesEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "label")
@Data
public class Label {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "l_id")
	private long l_Id;
	private String labelName;
	private long u_id;

	@JsonIgnore
	@ManyToMany(mappedBy = "labelsList",fetch = FetchType.LAZY)
	private List<Note> noteList;
	    
}