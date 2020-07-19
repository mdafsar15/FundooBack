
package com.bridgelabz.fundoo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "u_id")
	private long id;

	@NotEmpty
	@Column(name = "fname")
	private String fname;

	@NotEmpty
	@Column(name = "lname")
	private String lname;

	@Email
	@Column(unique = true)
	private String email;

	@NotEmpty
	@Column(name = "password")
	private String password;

	@NotEmpty
	@Column(name="mob_number")
	private String mob_number;

	@CreationTimestamp
	@Column(name = "registrationDate")
	private LocalDateTime registeredDate;
	
	@UpdateTimestamp
	@Column(name = "updatedDate")
	private LocalDateTime updatedDate;

	@Column(nullable = false)
	private boolean isVerified;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "u_id")
	private List<Note> notes;

	@JsonIgnore
	@OneToMany
	@JoinColumn(name = "u_id")
	private List<Label> labels;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "collaborator_note", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "note_id") })
	private List<Note> collaboratedNotes;

}
