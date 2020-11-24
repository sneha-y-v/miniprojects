package com.sneha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.sneha.model.PptUpload;

@SpringBootApplication
@EnableConfigurationProperties({
    PptUpload.class
})

public class invisionApplication   {
    
	public static void main(String[] args) {
		SpringApplication.run(invisionApplication .class, args);
	}

    
}
