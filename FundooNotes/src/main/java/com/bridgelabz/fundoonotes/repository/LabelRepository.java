package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;

public interface LabelRepository extends JpaRepository<LabelInformation, Integer> {

}
