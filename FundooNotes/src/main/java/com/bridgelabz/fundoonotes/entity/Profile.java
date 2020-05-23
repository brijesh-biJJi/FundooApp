package com.bridgelabz.fundoonotes.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long profileId;

	private String profilePicName;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId")
	private User userInfo;

	public Profile(String profilePicName, User user) {
		super();
		this.profilePicName = profilePicName;
		this.userInfo = user;
	}

	public Profile() {
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getProfilePicName() {
		return profilePicName;
	}

	public void setProfilePicName(String profilePicName) {
		this.profilePicName = profilePicName;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}
	
	
}
