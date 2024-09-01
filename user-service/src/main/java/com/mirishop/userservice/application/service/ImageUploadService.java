package com.mirishop.userservice.application.service;

import static com.mirishop.userservice.common.constants.UserConstants.DEFAULT_PROFILE_IMAGE_PATH;

import com.mirishop.userservice.common.exception.CustomException;
import com.mirishop.userservice.common.exception.ErrorCode;
import com.mirishop.userservice.common.util.FileUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageUploadService {

    private final Path imageDirectory;
    private final Path tempDirectory;

    public ImageUploadService() {
        this.imageDirectory = Paths.get("uploads/images").toAbsolutePath().normalize();
        this.tempDirectory = Paths.get("uploads/temp").toAbsolutePath().normalize();
        createDirectories();
    }

    private void createDirectories() {
        try {
            Files.createDirectories(imageDirectory);
            Files.createDirectories(tempDirectory);
        } catch (IOException e) {
            log.error("이미지 저장 폴더 생성 실패");
            throw new CustomException(ErrorCode.DIRECTORY_CREATION_FAILURE);
        }
    }

    /**
     * 파일을 받아서 확장자를 검증하고, UUID형태로 temp에 파일을 저장하는 메소드
     */
    public String uploadTempImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return DEFAULT_PROFILE_IMAGE_PATH;
        }

        if (!isValidImageType(file)) {
            throw new CustomException(ErrorCode.UNSUPPORTED_IMAGE_FORMAT);
        }

        String filename = FileUtils.generateUniqueFileName(file.getOriginalFilename());
        Path tempFilePath = tempDirectory.resolve(filename);
        Files.copy(file.getInputStream(), tempFilePath);

        return filename; // 저장된 이미지 파일명 반환
    }

    /**
     * 파일을 받아서 확장자를 검증하고, UUID형태로 파일을 저장하는 메소드
     */
    public String confirmImageUpload(String tempPath, String userId) throws IOException {
        Path source = Paths.get(tempPath);
        String filename = userId + "_" + source.getFileName();
        Path destination = imageDirectory.resolve(filename);

        Files.move(source, destination);
        return filename;
    }

    // 파일명은 jpg, png, gif만 허용
    private boolean isValidImageType(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
            contentType.contains("image/jpeg") ||
                contentType.contains("image/png") ||
                contentType.contains("image/gif"));
    }
}
