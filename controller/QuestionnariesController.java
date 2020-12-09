package com.sneha.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sneha.model.Questionnaries;
import com.sneha.model.QuestionnariesParticipant;
import com.sneha.service.QuestionnariesService;

@RestController
@RequestMapping(value = "/questionnaries")
public class QuestionnariesController {

	@Autowired
	private QuestionnariesService questionService;
	
	@GetMapping("/new")
	public int newQuestion(){

		return questionService.newQuestionnaries();
	}
	
    @PutMapping("/save")
    public ResponseEntity<?> addQuestion(@RequestBody Questionnaries questionnaries,@RequestParam int questionId) {
    	
    	return questionService.add(questionnaries,questionId);
    }
    
    @PutMapping("/uploadParticipant")
    public ResponseEntity<?> uploadParticipant(@RequestBody Set<String> participantIds, @RequestParam int questionId) {   
    	
    	return questionService.uploadParticipant(participantIds,questionId);
    }
	
    @PutMapping("/uploadPpt")
    public ResponseEntity<String> uploadSingleFile(@RequestParam("file") MultipartFile file,@RequestParam("questionId") int questionId) {
	    int questionnariesId = questionId;
	    String fileName = questionService.storeFile(file);
	
	    String pptDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
	            .path("/questionnaries/download-file/")
	            .path(fileName)
	            .toUriString();	    
	    return questionService.savePpt(questionnariesId, pptDownloadUrl);
	    
	  }
    
    @PutMapping("/publish")
    public ResponseEntity<?> publishQuestionnaries(@RequestParam int questionId) {         
    	
    	return questionService.publishQuestionnaries(questionId);
    }

    @GetMapping("/remainder")
    public ResponseEntity<String> remainder(@RequestParam int questionId)
    {
    	return questionService.sendRemainder(questionId);
    }
    
    @GetMapping("/report")
    @ResponseBody
    public ResponseEntity<?> report(@RequestParam int questionId) throws IOException {
	
    	return  questionService.generateReport(questionId);
    }
    
    @GetMapping("/download-file/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = questionService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.print("Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
