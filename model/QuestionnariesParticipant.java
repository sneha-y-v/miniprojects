package com.sneha.model;

import javax.persistence.*;

@Entity
public class QuestionnariesParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;    
   
    @Column(name = "question_id")
    private int questionnariesId;

    @Column(name = "participant_id")
    private String participantId;

    private boolean status = false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionnariesId() {
		return questionnariesId;
	}

	public void setQuestionnariesId(int questionId) {
		this.questionnariesId = questionId;
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}

	
}
