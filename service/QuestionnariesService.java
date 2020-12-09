package com.sneha.service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sneha.model.PptUpload;
import com.sneha.model.Questionnaries;
import com.sneha.model.QuestionnariesParticipant;
import com.sneha.model.UserDao;
import com.sneha.repository.QuestionnariesParticipantRepo;
import com.sneha.repository.QuestionnariesRepository;
import com.sneha.repository.UserRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Service
public class QuestionnariesService {

	@Autowired
	private QuestionnariesRepository questionRepo;	
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private QuestionnariesParticipantRepo questionnariesParticipantsRepo;
	
	@Autowired
	private UserRepository userRepo;

	private final Path fileStorageLocation;
	
	@Autowired
    public QuestionnariesService(PptUpload ppt) throws FileUploadException {
        this.fileStorageLocation = Paths.get(ppt.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileUploadException("Unable to create the directory where the uploaded files will be stored.", ex);
        }
    }
	public int newQuestionnaries() {
       long count= questionRepo.count();
       Questionnaries questionnaries = new Questionnaries();
       questionnaries.setQuestionId((int)count+1);
       questionRepo.save(questionnaries);
       return questionnaries.getQuestionId();
	}
	public ResponseEntity<?> add(Questionnaries question,int questionId) {
		try{
			Optional<Questionnaries> questionnariesOptional= questionRepo.findById(questionId);
			Questionnaries questionnaries = questionnariesOptional.get();
			questionnaries.setTitle(question.getTitle());
			questionnaries.setDescription(question.getDescription());
			questionnaries.setButtonText(question.getButtonText());
			questionnaries.setButtonTitle(question.getButtonTitle());
			questionnaries.setCheckBoxText(question.getCheckBoxText());
			questionnaries.setStartDate(question.getStartDate());
			questionnaries.setEndDate(question.getEndDate());
			questionnaries.setMailBody(question.getMailBody());
			questionRepo.save(questionnaries);
			return new ResponseEntity<>(questionnaries,HttpStatus.OK);
	   	}catch(NoSuchElementException e) {
	   		return new ResponseEntity<>("invalid QuestionId : "+question.getQuestionId(),HttpStatus.BAD_REQUEST);
	   	}
		
	  }
	
	public ResponseEntity<?> uploadParticipant(Set<String> participantIds, int questionId) {	
		for(String participantId : participantIds) {

			    if(userRepo.findById(participantId).isEmpty()) {

			    	return new ResponseEntity<>("Invalid userId: "+participantId,HttpStatus.BAD_REQUEST);				    
			    }
			    else {

			    	if(questionnariesParticipantsRepo.updateStatus(participantId, questionId)==null) {
						
						QuestionnariesParticipant questionnariesParticipants = new QuestionnariesParticipant();
						System.out.println(questionnariesParticipants);
						questionnariesParticipants.setParticipantId(participantId);
						questionnariesParticipants.setQuestionnariesId(questionId);
						questionnariesParticipantsRepo.save(questionnariesParticipants);
					}else {
						QuestionnariesParticipant questionnariesParticipant=questionnariesParticipantsRepo.updateStatus(participantId, questionId);
						questionnariesParticipant.setParticipantId(participantId);
						questionnariesParticipant.setQuestionnariesId(questionId);
						questionnariesParticipantsRepo.save(questionnariesParticipant);
					}
			    }
					
		}
	
        return new ResponseEntity<>(participantIds,HttpStatus.OK);
	}
    public ResponseEntity<String> savePpt(int questionId, String pptDownloadUrl) {
	    try {
	    	Optional<Questionnaries> q= questionRepo.findById(questionId);
	    	Questionnaries questionnaries = q.get();
	    	questionnaries.setPpt(pptDownloadUrl);
	    	questionRepo.save(questionnaries);
	    	return new ResponseEntity<>(pptDownloadUrl,HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<>("Invalid questionId: "+questionId,HttpStatus.BAD_REQUEST);
		}
	
    }
	
	public ResponseEntity<String> publishQuestionnaries(int questionId) {
		try{
			List<QuestionnariesParticipant> questionnariesParticipants = questionnariesParticipantsRepo.findByQuestionId(questionId);
			for (QuestionnariesParticipant questionnariesParticipant : questionnariesParticipants) {
				Optional<UserDao> user = userRepo.findById(questionnariesParticipant.getParticipantId());
				String email = user.get().getUsername();
				Optional<Questionnaries> question = questionRepo.findById(questionId);
				String subject = question.get().getTitle();
				String message = question.get().getMailBody();
				emailService.sendMail(email,subject, message);
			}
            return new ResponseEntity<>("Published questionnaries successfully!!!",HttpStatus.OK);
		}catch(NoSuchElementException e) {
				return new ResponseEntity<>("Invalid questionId: "+questionId,HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<String> sendRemainder(int questionId) {
	try{
		List<QuestionnariesParticipant> questionParticipants = questionnariesParticipantsRepo.findByQuestionId(questionId);
		for(QuestionnariesParticipant questionnariesParticipant : questionParticipants) {
			Optional<Questionnaries> question = questionRepo.findById(questionId);
			Optional<UserDao> participant = userRepo.findById(questionnariesParticipant.getParticipantId());
			if(questionnariesParticipant.getStatus() == false) {
				String email = participant.get().getUsername();
				String subject = "Remainder for accepting a "+ question.get().getTitle();
				String message = "Hi "+ participant.get().getName()+",\n\nOnly "+question.get().getRemainder()+" day remaining for accepting "+ question.get().getTitle()+"\n\nPlease accept!!!";
				emailService.sendMail(email,subject, message);
				
			}else {
				return new ResponseEntity<>("Participant : "+participant.get().getEmployeeCode()+" has already accepted the question id"+questionId,HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Sent remainder email successfully!!!",HttpStatus.OK);
	  }catch(NoSuchElementException e) {
			return new ResponseEntity<>("Invalid questionId: "+questionId,HttpStatus.BAD_REQUEST);
	  }
	}

	public ResponseEntity<?> generateReport(int questionId) {
	    if(questionRepo.findById(questionId).isPresent()){
			List<QuestionnariesParticipant> questionnariesParticipants = questionnariesParticipantsRepo.findByQuestionId(questionId);
			return new ResponseEntity<>(questionnariesParticipants,HttpStatus.OK);
	    }else{
				return new ResponseEntity<>("Invalid questionId: "+questionId,HttpStatus.BAD_REQUEST);
		 }		
	}
		
	public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
	            if(fileName.contains("..")) {
	                throw new FileStorageException ("Sorry! Filename contains invalid path sequence " + fileName);
	            }

	            Path targetLocation = this.fileStorageLocation.resolve(fileName);
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

	            return fileName;
          } catch (IOException ex) {
            throw new FileStorageException ("Could not store file " + fileName + ". Please try again!", ex);
        }
	 }

	
   public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MentionedFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MentionedFileNotFoundException("File not found " + fileName, ex);
        }
    }
   
	public class FileStorageException extends RuntimeException {
	    public FileStorageException(String message) {
	        super(message);
	    }

	    public FileStorageException(String message, Throwable cause) {
	        super(message, cause);
	    }
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public class MentionedFileNotFoundException extends RuntimeException {
	    public MentionedFileNotFoundException(String message) {
	        super(message);
	    }

	    public MentionedFileNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
	}


}
