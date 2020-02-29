package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotes.entity.Profile;

public interface ProfileRepo extends JpaRepository<Profile, Long> {

}
