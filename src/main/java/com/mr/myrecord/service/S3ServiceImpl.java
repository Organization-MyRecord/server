package com.mr.myrecord.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@RequiredArgsConstructor
@Component
public class S3ServiceImpl implements S3Service{

    private final AmazonS3 amazonS3;
    //S3Component 주입
    private final S3Component component;

    // S3Component에 추가한 버킷 정보를 가져와서 putObject를 통해 AWS S3로 파일을 업로드
    //aws-cloud-starter-aws 라이브러리에서 제공하는 AmazonS3Client를 사용해서 파일 업로드
    @Override
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        amazonS3.putObject(new PutObjectRequest(component.getBucket(), fileName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    // 업로드된 파일의 URI 가져오기
    @Override
    public String getFileUrl(String fileName) {
        return amazonS3.getUrl(component.getBucket(), fileName).toString();
    }
}
