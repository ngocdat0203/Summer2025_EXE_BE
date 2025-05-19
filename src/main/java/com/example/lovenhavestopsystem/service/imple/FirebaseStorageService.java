package com.example.lovenhavestopsystem.service.imple;

import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseStorageService {
    @Value("${firebase.bucket-name}")
    private String bucketName;

    @Value("${firebase.folder-name}")
    private String folderName;

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = folderName + "/" + file.getOriginalFilename();

        InputStream inputStream = file.getInputStream();

        StorageClient.getInstance()
                .bucket(bucketName)
                .create(fileName, inputStream, file.getContentType());

        return "https://firebasestorage.googleapis.com/v0/b/" + bucketName +
                "/o/" + fileName.replace("/", "%2F") + "?alt=media";
    }
}
