package com.selfsell.mgt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

		return new ResponseEntity<byte[]>(result, HttpStatus.OK);
	}
}
