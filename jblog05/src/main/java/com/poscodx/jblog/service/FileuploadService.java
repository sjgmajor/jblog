package com.poscodx.jblog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.exception.FileUploadServiceException;

@Service
public class FileuploadService {
	private static String SAVE_PATH = "/jblog-uploads";
	private static String URL_PATH = "/assets/upload-images";

	public String restore(MultipartFile file) {
		
		String url = null;
		OutputStream os = null;
		
		try {
			File uploadDirectory = new File(SAVE_PATH);
			if(!uploadDirectory.exists()) {
				uploadDirectory.mkdir();
			}
			
			if(file.isEmpty()) {
				return url;
			}
			
			String originFilename = file.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf(".") + 1);
			String saveFilename = generateSaveFilename(extName);
			
			byte[] data = file.getBytes();
			os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(data);
			
			url = URL_PATH + "/" + saveFilename;
			
		} catch(IOException ex) {
			throw new FileUploadServiceException(ex.toString());
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return url;
	}

	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}
}
