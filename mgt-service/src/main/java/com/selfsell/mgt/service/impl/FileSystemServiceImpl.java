package com.selfsell.mgt.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.breeze.bms.mgt.exception.BusinessException;
import com.google.common.io.Files;
import com.selfsell.mgt.service.FileSystemService;

@Component
public class FileSystemServiceImpl implements FileSystemService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Value("${file.system.path}")
	String fileSystemPath;

	@Override
	public String save(String path, MultipartFile file) {
		if (file == null || file.isEmpty()) {
			log.info("没有选择上传文件【{}】", path);
			return "";
		}
		String fileName = UUID.randomUUID().toString() + "." + Files.getFileExtension(file.getOriginalFilename());
		String filePath = path + "/" + fileName;

		File fileParent = new File(fileSystemPath, path);
		if (!fileParent.exists()) {
			fileParent.mkdirs();
		}

		File result = new File(fileParent, fileName);
		if (!result.exists()) {
			try {
				result.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException("创建文件【{}】失败", filePath);
			}
		}

		// 保存文件
		try {
			file.transferTo(result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("保存文件【{}】失败", filePath);
		}

		return filePath;
	}

	@Override
	public byte[] fileResult(String path) {
		File file = new File(fileSystemPath, path);
		if (file.exists()) {
			try {
				return Files.toByteArray(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String path = "/image/tet";
		System.out.println(path.replaceAll("/", Matcher.quoteReplacement(File.separator)));
	}
}
