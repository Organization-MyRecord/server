package com.mr.myrecord.controller;

import com.mr.myrecord.model.Header;
import com.mr.myrecord.model.request.DirectoryRequest;
import com.mr.myrecord.model.response.DirectoryListResponse;
import com.mr.myrecord.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    /**
     * 사용자 폴더 생성 api
     * 로그인 권한 필요
     */
    @PostMapping("/directory")
    public Header<Boolean> create(@RequestBody DirectoryRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();

            if(directoryService.create(email, request) == true) {
                return Header.OK(directoryService.create(email, request));
            }
            else {
                return Header.ERROR("이미 존재하는 디렉토리 입니다.");
            }
        }
        catch(Exception e) {
            return Header.ERROR("로그인 권한이 필요합니다.");
        }
    }

    /**
     * 사용자 폴더 수정 api
     * 로그인 권한 필요
     */
    @PutMapping("/directory/{name}")
    public Header<Boolean> update(@PathVariable String name, @RequestBody DirectoryRequest directoryRequest) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();

            if(directoryService.update(email, name, directoryRequest)==true) {
                return Header.OK(directoryService.update(email, name, directoryRequest));
            }
            else {
                return Header.ERROR("폴더명을 입력하지 않았습니다.");
            }
        }
        catch(Exception e) {
            return Header.ERROR("로그인 권한이 필요합니다.");
        }
    }

    /**
     * 사용자 폴더 삭제 api
     * 로그인 권한 필요
     */
    @DeleteMapping("/directory/{name}")
    public Header<Boolean> delete(@PathVariable String name) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();

            if(directoryService.delete(email, name) == true) {
                return Header.OK(directoryService.delete(email, name));
            }
            else {
                return Header.ERROR("폴더 안에 게시물이 존재합니다.");
            }

        }
        catch(Exception e) {
            return Header.ERROR("로그인 권한이 필요합니다.");
        }
    }

    /**
     * 사용자 폴더 목록 가져오기
     */
    @GetMapping("/directory/{userEmail}")
    public Header<DirectoryListResponse> read(@PathVariable String userEmail) {
        return Header.OK(directoryService.read(userEmail));
    }

}
