package com.sneha.model;


import java.util.Set;

import javax.persistence.*;

@Entity
public class Questionnaries {

    @Id
    @GeneratedValue
	private int questionId;
    
    @Column
    private String title;
	
    @Column
    private String ppt;
    
	@OneToMany(mappedBy = "questionnaries", cascade = CascadeType.ALL)
	private Set<QuestionnariesParticipant> questionnariesParticipant;

	public int getId() {
		return questionId;
	}

	public void setId(int id) {
		questionId = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPpt() {
		return ppt;
	}

	public void setPpt(String ppt) {
		this.ppt = ppt;
	}

	public Set<QuestionnariesParticipant> getQuestionnariesParticipant() {
		return questionnariesParticipant;
	}

	public void setQuestionnariesParticipant(Set<QuestionnariesParticipant> questionnariesParticipant) {
		this.questionnariesParticipant = questionnariesParticipant;
	}
	
}
