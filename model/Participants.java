package com.sneha.model;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Participants {

	@Id
    private String employeeId;

    @Column
    private String email;

    @OneToMany(mappedBy = "participants", cascade = CascadeType.ALL)
    private Set<QuestionnariesParticipant> questionnariesParticipantss;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<QuestionnariesParticipant> getQuestionnariesParticipantss() {
		return questionnariesParticipantss;
	}

	public void setQuestionnariesParticipantss(Set<QuestionnariesParticipant> questionnariesParticipantss) {
		this.questionnariesParticipantss = questionnariesParticipantss;
	}

    
}
