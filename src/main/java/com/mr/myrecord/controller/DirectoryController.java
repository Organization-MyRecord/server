package com.mr.myrecord.controller;

import com.mr.myrecord.model.request.DirectoryRequest;
import com.mr.myrecord.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    @PostMapping("/directory")
    public boolean create(DirectoryRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();

        return directoryService.create(email, request);
    }

    @DeleteMapping("/directory/{name}")
    public boolean delete(@PathVariable String name) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();

        return directoryService.delete(email, name);

    }

}
