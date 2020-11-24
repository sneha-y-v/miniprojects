package com.sneha.model;

import javax.persistence.*;

@Entity
public class QuestionnariesParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    private Questionnaries questionnaries ;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_id")
    private Participants participants;

    private boolean status = false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Questionnaries getQuestionnaries() {
		return questionnaries;
	}

	public void setQuestionnaries(Questionnaries questionnaries) {
		this.questionnaries = questionnaries;
	}

	public Participants getParticipants() {
		return participants;
	}

	public void setParticipants(Participants participants) {
		this.participants = participants;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}   
	
}
