package com.honsoft.web.model;

import org.springframework.web.multipart.MultipartFile;

public class MyCommand {
	
	MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	

}
