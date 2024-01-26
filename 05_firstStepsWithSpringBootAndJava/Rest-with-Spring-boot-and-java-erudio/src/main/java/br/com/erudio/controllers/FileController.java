package br.com.erudio.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.erudio.data.vo.v1.UploadFileResponseVO;
import br.com.erudio.services.FileStorageServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "File Endpoint", description = "endpoint for request Downloads and uploads")
@RestController
@RequestMapping("/api/file/v1")

public class FileController {
	@Autowired
	FileStorageServices service;
	
	@PostMapping("/uploadFile")
	public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
		var fileName = service.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/file/downloadFile/")
				.path(fileName)
				.toUriString(); 
		return
			new UploadFileResponseVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}
	@PostMapping("/uploadMultFiles")
	public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files")  MultipartFile [] files) {
		
		
		return Arrays.asList(files)
				.stream().map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}
	// o ':.+' é utilizado para o spring conseguir interpretar corretamente a extensão do arquivo.
	@GetMapping("/DownloadFile/{filename:.+}")
	public ResponseEntity<Resource> uploadMultipleFiles(@PathVariable String Filename, HttpServletRequest request) {
		
		Resource resource = service.loadFileAsResource(Filename);
		String contentType = "";
		
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {}
		
		if(contentType.isBlank()) contentType = "application/octet-stream";
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
