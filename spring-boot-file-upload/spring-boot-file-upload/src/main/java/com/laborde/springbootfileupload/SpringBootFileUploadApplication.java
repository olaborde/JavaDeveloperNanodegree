package com.laborde.springbootfileupload;

import com.laborde.springbootfileupload.controller.UploadController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
@ComponentScan({"com.laborde.springbootfileupload", "controller"})
public class SpringBootFileUploadApplication {

	public static void main(String[] args) {
		new File(UploadController.uploadDirectory).mkdir();
		SpringApplication.run(SpringBootFileUploadApplication.class, args);
	}

}
