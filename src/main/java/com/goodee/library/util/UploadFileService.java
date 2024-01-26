package com.goodee.library.util;

import java.io.File;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(UploadFileService.class);
	
	public String upload(MultipartFile file) {
		LOGGER.info("[UploadFileService] upload();");
		
		String result = "";
		
		/* 파일을 서버에 저장 */
		
		// 원(original) 파일명 저장
		String fileOriName = file.getOriginalFilename();
		// 파일 확장자 저장
		String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length()); 
		// 파일 저장 위치 지정
		String uploadDir = "/var/lib/tomcat9/webapps/upload/";
		
		// 시스템 파일명 저장 (범용 고유 식별자(Universally Unique Identifier, UUID) 사용)
		UUID uuid = UUID.randomUUID();
		// 하이픈이 마이너스로 읽힐 수 있기 때문에 하이픈(-)이 이름에 들어올 경우 빈문자열로 변경
		String uniqueName = uuid.toString().replaceAll("-", "");
		
		// 새로운 파일 생성 (껍데기)
		File saveFile = new File(uploadDir + uniqueName + fileExtension);
		
		// 디렉터리가 없는 경우 새로 생성
		if(!saveFile.exists()) saveFile.mkdirs();
		
		// 파일 관련된건 try-catch문 필요
		try {
			// 받아온 파일 정보를 saveFile에 저장 (알맹이)
			file.transferTo(saveFile);
			// 파일 트랜스퍼가 정상적으로 완료된 경우 새로 생성한 파일명과 확장자를 저장
			result = uniqueName + fileExtension;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 새로 생성한 파일명 또는 트랜스퍼 실패시 빈문자열("") 반환
		return result;
	}

}
