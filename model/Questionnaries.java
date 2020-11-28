package com.sneha.model;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Questionnaries {

    @Id
    @GeneratedValue
	private int questionId;
    
    @Column
    private String title;  
    
    @Column
    private String description;
    
    @Column
    private String buttonTitle;
    
    @Column
    private String buttonText;
    
    @Column
    private String CheckBoxText;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date startDate;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date endDate;
    
    @Column
    private int remainder;
    
    @Column
    private String ppt;
    
    @Column
    private String mailBody;
    
    
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

	
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getButtonTitle() {
		return buttonTitle;
	}

	public void setButtonTitle(String buttonTitle) {
		this.buttonTitle = buttonTitle;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public String getCheckBoxText() {
		return CheckBoxText;
	}

	public void setCheckBoxText(String checkBoxText) {
		CheckBoxText = checkBoxText;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getRemainder() {
		return remainder;
	}

	public void setRemainder(int remainder) {
		this.remainder = remainder;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public String getPpt() {
		return ppt;
	}

	public void setPpt(String ppt) {
		this.ppt = ppt;
	}
	
}
