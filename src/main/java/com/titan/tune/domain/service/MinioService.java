package com.titan.tune.domain.service;

import com.titan.tune.common.exception.BusinessException;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;
    @Value("${minio.url}")
    private String minioUrl;

    public String uploadFile(MultipartFile file) {
        try {
            // Vérifier si le bucket existe, sinon le créer
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // Générer un nom de fichier unique
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Uploader le fichier
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // Retourner l'URL publique (ou interne)
            return minioUrl + "/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            throw new BusinessException("Erreur lors de l'upload du fichier : " + e.getMessage());
        }
    }
}