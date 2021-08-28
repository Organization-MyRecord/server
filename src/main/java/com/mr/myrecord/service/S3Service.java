package com.mr.myrecord.service;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

/**
 * MultipartFile 에서의 InputStream을 가져와서
 * 실제 AWS S3로 파일을 전송하는 로직 담당
 * 런타임 시점에서 FileUploadService에서 주입받아 오브젝트 관계를 갖도록 함
 */
public interface S3Service {

    void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);

    String getFileUrl(String fileName);

}
