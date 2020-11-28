package com.sneha.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.sneha.repository.QuestionnariesRepository;
import com.sneha.service.QuestionnariesService;

@RestController
@RequestMapping(value = "/questionnaries")
public class QuestionnariesController {

	@Autowired
	private QuestionnariesService questionService;
	
	@Autowired
	private QuestionnariesRepository questionRepo;
	
	@GetMapping("/new")
	public int newQuestion(){
		Questionnaries newQuestionId = new Questionnaries();		
	    questionRepo.save(newQuestionId);
		return newQuestionId.getId();
	}
	
    @PutMapping("/save")
    public void addQuestion(@RequestBody Questionnaries questionnaries) {
    	questionService.add(questionnaries);
    }
    
    @PutMapping("/uploadParticipant")
    public void uploadParticipant(@RequestBody List<String> participantIds, @RequestParam int questionId) {    	

    	questionService.uploadParticipant(participantIds,questionId);
    }
	
    @PutMapping("/uploadPpt")
    public void uploadSingleFile(@RequestParam("file") MultipartFile file,@RequestParam("questionId") int questionId) {
	    int questionnariesId = questionId;
	    String fileName = questionService.storeFile(file);
	
	    String pptDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
	            .path("/download-file/")
	            .path(fileName)
	            .toUriString();
	    questionService.savePpt(questionnariesId, pptDownloadUrl);
	     
	  }
    
    @PutMapping("/publish")
    public void publishQuestionnaries(@RequestParam int questionId) {         
    	questionService.publishQuestionnaries(questionId);
    }

    @GetMapping("/remainder")
    public void remainder(@RequestParam int questionId)
    {
    	questionService.sendRemainder(questionId);
    }
    
    @GetMapping("/report")
    @ResponseBody
    public List<QuestionnariesParticipant> report(@RequestParam int questionId) throws IOException {
    	List <QuestionnariesParticipant>questionnariesParticipants = questionService.generateReport(questionId);
		
    	return questionnariesParticipants;
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
