package com.sneha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sneha.model.Participants;

@Repository
public interface ParticipantRepository extends JpaRepository<Participants, Integer> {

}
