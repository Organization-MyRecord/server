package com.mr.myrecord.controller;

import com.mr.myrecord.model.request.DirectoryRequest;
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

    @PostMapping("/directory")
    public boolean create(@RequestBody DirectoryRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();

        return directoryService.create(email, request);
    }

    @PutMapping("/directory/{name}")
    public boolean update(@PathVariable String name, @RequestBody DirectoryRequest directoryRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();

        return directoryService.update(email, name, directoryRequest);
    }

    @DeleteMapping("/directory/{name}")
    public boolean delete(@PathVariable String name) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();

        return directoryService.delete(email, name);

    }

}
