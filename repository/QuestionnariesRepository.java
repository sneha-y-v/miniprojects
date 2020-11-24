package com.sneha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sneha.model.Questionnaries;

@Repository
public interface QuestionnariesRepository extends JpaRepository<Questionnaries, Integer>{

}
