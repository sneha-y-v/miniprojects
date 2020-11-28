package com.sneha.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sneha.model.Questionnaries;
import com.sneha.service.SafeguardingCodeService;

@RestController
public class SafeguardingCodeController {

	@Autowired
	private SafeguardingCodeService safeguardingCodeService; 

	@GetMapping("/pendingList")
	@ResponseBody
	public Map<Integer, String> getPendingQuestionnariesList(@RequestParam String empId) {
		return safeguardingCodeService.getPendingList(empId);
	}
	
	@GetMapping("/completedList")
	@ResponseBody
	public Map<Integer, String> getCompletedQuestionnariesList(@RequestParam String empId) {
		return safeguardingCodeService.getCompletedList(empId);
	}
	
	@GetMapping("/pendingQuestion")
	@ResponseBody
	public Questionnaries getPendingQuestion(@RequestParam int questionId) {
		return safeguardingCodeService.getQuestion(questionId);
	}
	
	@GetMapping("/completedQuestion")
	@ResponseBody
	public Questionnaries getCompletedQuestion( @RequestParam int questionId) {
		return safeguardingCodeService.getQuestion(questionId);
	}
	
	@PutMapping("/accept")
	public ResponseEntity<?> setAction(@RequestParam String empId, @RequestParam int questionId){	
		safeguardingCodeService.changeStatus(empId,questionId);
		return ResponseEntity.ok("Thank you for accepting the terms and condition");
						
	}
	
}
