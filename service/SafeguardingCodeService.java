package com.sneha.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sneha.model.Questionnaries;
import com.sneha.model.QuestionnariesParticipant;
import com.sneha.model.UserDao;
import com.sneha.repository.QuestionnariesParticipantRepo;
import com.sneha.repository.QuestionnariesRepository;

@Service
public class SafeguardingCodeService {

	@Autowired 
	QuestionnariesParticipantRepo questionnariesParticipantRepo;
	
	@Autowired
	QuestionnariesRepository questionRepo;
	
	public Map<Integer, String>  getPendingList(String empId) {
        
		Map<Integer,String> pendingQuestionnariesList = new HashMap<>(); 
		List<QuestionnariesParticipant> pending = questionnariesParticipantRepo.findQuestionnaries(empId,false);

		for(QuestionnariesParticipant questionnariesParticipant : pending) {
			Optional<Questionnaries> question = questionRepo.findById(questionnariesParticipant.getQuestionnariesId()); 
			pendingQuestionnariesList.put(question.get().getQuestionId(),question.get().getTitle());
		}
		return pendingQuestionnariesList;

	}

	public Map<Integer, String> getCompletedList(String empId) {
		
		Map<Integer, String> completedQuestionnariesList = new HashMap<>(); 
		List<QuestionnariesParticipant> pending = questionnariesParticipantRepo.findQuestionnaries(empId,true);

		for(QuestionnariesParticipant questionnariesParticipant : pending) {
			Optional<Questionnaries> question = questionRepo.findById(questionnariesParticipant.getQuestionnariesId()); 
			completedQuestionnariesList.put(question.get().getQuestionId(),question.get().getTitle());
		}
		return completedQuestionnariesList;
	}

	public Questionnaries getQuestion(int questionId) {
		
		Optional<Questionnaries> question = questionRepo.findById(questionId); 
		return question.get();

	}

	public void changeStatus(String empId, int questionId) {

	  QuestionnariesParticipant questionnariesParticipant = questionnariesParticipantRepo.updateStatus(empId, questionId);
      questionnariesParticipant.setStatus(true);  
      questionnariesParticipantRepo.save(questionnariesParticipant);
	  
	}

}
