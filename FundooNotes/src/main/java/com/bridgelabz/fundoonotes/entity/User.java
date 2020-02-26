package com.bridgelabz.fundoonotes.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */

@Data
@Entity
@Table(name="userinfo")
@Component
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userid;
	private String name;
	private String password;
	private long phone;
	private String email;
	
	@Column(columnDefinition = "boolean Default false", nullable=false)
	private boolean isVerified;
	private LocalDateTime dateTime;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private List<NoteInformation> noteList;
}
