package com.mulcam.finalproject.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.uuid.Generators;
import com.mulcam.finalproject.dto.ImageDTO;

@Service
public class ImageUploadUtil {

	@Value("${spring.servlet.multipart.location}")
	private String location;

	/** 이미지 로컬 저장 */
	public List<ImageDTO> LocalSaveFiles(List<MultipartFile> imgs) {
		File path = getPath();
		List<ImageDTO> list = new ArrayList<>();

		imgs.forEach(img -> {
			String inputFile = img.getOriginalFilename();
			String ext = "." + inputFile.substring(inputFile.lastIndexOf(".") + 1);
			UUID uuid = Generators.timeBasedGenerator().generate();
			String newfileName = uuid + ext;

			try {
				if (!path.exists())
					path.mkdir(); // 폴더가 없으면 폴더 생성
				img.transferTo(new File(path, newfileName));

			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

			ImageDTO imgDTO = ImageDTO.builder()
					.ext(ext)
					.uuid(uuid)
					.path(path)
					.inputFile(inputFile)
					.build();
			list.add(imgDTO);
		});
		return list;
	}

	// 날짜별 폴더 생성
	public File getPath() {
		LocalDate localdate = LocalDate.now();
		File path = new File(location + File.separator + localdate.toString());
		return path;
	}
	
	/** 이미지 로컬 삭제 */
	public ResponseEntity<Boolean> removeFile(ImageDTO imgDTO) {
		File file = new File(location + File.separator + imgDTO.getSaveDate() + File.separator
				+ imgDTO.getUuid().toString() + imgDTO.getExt());
		boolean result = file.delete();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
