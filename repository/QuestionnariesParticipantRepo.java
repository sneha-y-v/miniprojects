package com.sneha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sneha.model.QuestionnariesParticipant;

public interface QuestionnariesParticipantRepo extends JpaRepository<QuestionnariesParticipant,Integer> {

	long countByStatus(boolean status);
	
	@Query("FROM QuestionnariesParticipant WHERE question_id = ?1")
	List<QuestionnariesParticipant> findByQuestionId(int question_id);
	
    @Query("FROM QuestionnariesParticipant WHERE participant_id = ?1 and status = ?2")
    List<QuestionnariesParticipant> findQuestionnaries(String participant_id, boolean status);  
    
    @Query("FROM QuestionnariesParticipant WHERE participant_id = ?1 and question_id = ?2")
    QuestionnariesParticipant updateStatus(String participant_id,int question_id);  

}
