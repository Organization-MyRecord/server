package com.mr.myrecord.controller;

import com.mr.myrecord.model.Header;
import com.mr.myrecord.model.request.DirectoryRequest;
import com.mr.myrecord.model.response.DirectoryListResponse;
import com.mr.myrecord.model.response.DirectoryPostResponse;
import com.mr.myrecord.service.DirectoryService;
import com.mr.myrecord.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private PostService postService;

    /**
     * 사용자 폴더 생성 api
     * 로그인 권한 필요
     */
    @PostMapping("/directory")
    @ApiOperation(value = "디렉토리 만들김")
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
    @ApiOperation(value = "디렉토리 수정하기")
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
    @ApiOperation(value = "디렉토리 삭제하기")
    public Header<Boolean> delete(@PathVariable String name) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();

            if(directoryService.delete(email, name) == true) {
                return Header.OK(true);
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
    @ApiOperation(value = "사용자 폴더 목록 가져오기")
    public Header<DirectoryListResponse> read(@PathVariable String userEmail) {
        return Header.OK(directoryService.read(userEmail));
    }

    /**
     * 디렉토리 내 게시물 가져오기
     */
    @GetMapping("/directory")
    @ApiOperation(value = "디렉토리 누르면 내부 게시물 가져오기, 디렉토리 이름, 사용자 이메일 필수")
    public Header<DirectoryPostResponse> readPost(@RequestParam String directoryName,
                                                  @RequestParam String userEmail,
                                                  @PageableDefault(sort = "views", direction = Sort.Direction.DESC, size=10) Pageable pageable) {
        return Header.OK(postService.readPost(directoryName, userEmail, pageable));
    }
}
