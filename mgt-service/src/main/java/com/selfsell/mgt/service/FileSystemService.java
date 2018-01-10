package com.selfsell.mgt.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 系统文件服务
 * 
 * @author breeze
 *
 */
public interface FileSystemService {

	/**
	 * 保存文件
	 * 
	 * @param path
	 * @param file
	 * @return
	 */
	String save(String path, MultipartFile file);

	/**
	 * 获取文件
	 * 
	 * @param path
	 * @return
	 */
	byte[] fileResult(String path);

}
