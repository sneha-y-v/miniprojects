package com.sneha.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


	public Questionnaries() {
		super();
	}

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
	
}
