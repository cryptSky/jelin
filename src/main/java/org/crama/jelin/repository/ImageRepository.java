package org.crama.jelin.repository;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageRepository {

	String uploadImage(MultipartFile avatar) throws FileNotFoundException, IOException;

}
