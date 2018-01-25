package com.selfsell.mgt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.io.Files;
import com.selfsell.mgt.service.FileSystemService;

@Controller
@RequestMapping(value = "/file")
public class FileController {

	@Autowired
	FileSystemService fileSystemService;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@ResponseBody
	ResponseEntity<byte[]> show(@RequestParam(value = "path") String path) {
		byte[] result = fileSystemService.fileResult(path);

		HttpHeaders header = new HttpHeaders();
		String ext = Files.getFileExtension(path);
		if ("pdf".equalsIgnoreCase(ext))
			header.setContentType(MediaType.APPLICATION_PDF);
		if ("png".equalsIgnoreCase(ext) || "jpg".equalsIgnoreCase(ext)) {
			header.setContentType(MediaType.IMAGE_PNG);
		}

		return new ResponseEntity<byte[]>(result, header, HttpStatus.OK);
	}
}
