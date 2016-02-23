package org.crama.jelin.repository.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.crama.jelin.model.Constants;
import org.crama.jelin.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository("imageRepository")
public class FileSystemImageRepositoryImpl implements ImageRepository {

	private static final Logger logger = LoggerFactory.getLogger(FileSystemImageRepositoryImpl.class);
	
	@Autowired
	private ServletContext servletContext;
	
	@Override
	public String uploadImage(MultipartFile avatar) throws IOException {
		String filePath = Constants.IMAGE_PATH;
		String fileName = avatar.getOriginalFilename();
		String contextPath = servletContext.getRealPath("");
		filePath = contextPath + filePath;
		createDirectory(filePath);
		logger.info(filePath);
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
