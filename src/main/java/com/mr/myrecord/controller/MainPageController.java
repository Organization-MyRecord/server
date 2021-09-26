package com.mr.myrecord.controller;

import com.mr.myrecord.model.Header;
import com.mr.myrecord.model.entity.Post;
import com.mr.myrecord.model.repository.PostRepository;
import com.mr.myrecord.model.response.MainPageResponse;
import com.mr.myrecord.service.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class MainPageController {

    @Autowired
    private MainPageService mainPageService;

    @GetMapping("/main")
    public Header<MainPageResponse> mainPage() {
        // 로그인한 경우 리턴
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();
            return Header.OK(mainPageService.read(email));
        }
        // 로그인 안된 경우 리턴
        catch(Exception e) {
            return Header.OK(mainPageService.unLoginRead());
        }
    }

}
