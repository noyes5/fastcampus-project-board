package com.mirishop.user.member.service;

import com.mirishop.user.common.exception.EmailException;
import com.mirishop.user.common.exception.ErrorCode;
import com.mirishop.user.common.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    private final Path imageDirectory;

    public ImageUploadServiceImpl() {
        this.imageDirectory = Paths.get("uploads/images")
                .toAbsolutePath()
                .normalize();
        createDirectories();
    }

    private void createDirectories() {
        try {
            Files.createDirectories(imageDirectory);
        } catch (IOException e) {
            log.error("이미지 저장 폴더 생성 실패", e);
            throw new EmailException(ErrorCode.DIRECTORY_CREATION_FAILURE);
        }
    }

    /**
     * 파일을 받아서 확장자를 검증하고, UUID형태로 파일을 저장하는 메소드
     */
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new EmailException(ErrorCode.EMPTY_FILE_EXCEPTION);
        }

        if (!isValidImageType(file)) {
            throw new EmailException(ErrorCode.UNSUPPORTED_IMAGE_FORMAT);
        }

        String filename = FileUtils.generateUniqueFileName(file.getOriginalFilename());
        Path destinationFilePath = imageDirectory.resolve(filename);
        Files.copy(file.getInputStream(), destinationFilePath);

        return filename; // 저장된 이미지 파일명 반환
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
