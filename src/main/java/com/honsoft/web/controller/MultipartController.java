package com.honsoft.web.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.honsoft.web.model.MyCommand;

@RestController
public class MultipartController {

	@RequestMapping(value = { "/upload" }, method = {
			RequestMethod.POST }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String upload(@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println(file.getName());
		System.out.println(file.getSize());
		System.out.println(file.getOriginalFilename());
		
		byte[] data = file.getBytes();
		System.out.println(new String(data, Charset.forName("UTF-8")));
		
		return "success";
	}
	
	@RequestMapping(value = { "/upload2" }, method = {
			RequestMethod.POST }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String upload2(HttpServletRequest request) throws IOException {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartHttpServletRequest.getFile("file");
		
		System.out.println(file.getName());
		System.out.println(file.getSize());
		System.out.println(file.getOriginalFilename());
		
		byte[] data = file.getBytes();
		System.out.println(new String(data, Charset.forName("UTF-8")));
		
		return "success2";
	}
	
	@RequestMapping(value = { "/upload3" }, method = {
			RequestMethod.POST }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String upload3(MultipartHttpServletRequest request) throws IOException {
		MultipartFile file = request.getFile("file");
		
		System.out.println(file.getName());
		System.out.println(file.getSize());
		System.out.println(file.getOriginalFilename());
		
		byte[] data = file.getBytes();
		System.out.println(new String(data, Charset.forName("UTF-8")));
		
		return "success3";
	}
	
	@RequestMapping(value = { "/upload4" }, method = {
			RequestMethod.POST }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String upload4(MyCommand	command) throws IOException {
		MultipartFile file = command.getFile();
		
		System.out.println(file.getName());
		System.out.println(file.getSize());
		System.out.println(file.getOriginalFilename());
		
		byte[] data = file.getBytes();
		System.out.println(new String(data, Charset.forName("UTF-8")));
		
		return "success4";
	}
}
