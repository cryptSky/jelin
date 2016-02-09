package org.crama.jelin.repository.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.crama.jelin.model.Constants;
import org.crama.jelin.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository("imageRepository")
public class FileSystemImageRepositoryImpl implements ImageRepository {

	@Autowired
	private ServletContext servletContext;
	
	@Override
	public String uploadImage(MultipartFile avatar) throws IOException {
		String filePath = Constants.IMAGE_PATH;
		String fileName = avatar.getOriginalFilename();
		String contextPath = servletContext.getRealPath("");
		filePath = contextPath + filePath;
		createDirectory(filePath);
		System.out.println(filePath);
		BufferedOutputStream stream =
                 new BufferedOutputStream(new FileOutputStream(new File(filePath + fileName)));
		
        stream.write(avatar.getBytes());
        stream.close();
		return Constants.IMAGE_PATH + fileName;
	}
	
	private void createDirectory(String path) {
        File file = new File(path);
        file.mkdirs();
    }

}
