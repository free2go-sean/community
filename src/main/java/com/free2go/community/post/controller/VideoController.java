package com.free2go.community.post.controller;

import com.free2go.community.post.dto.VideoDto;
import com.free2go.community.post.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class VideoController {
    private final VideoService videoService;

    @PostMapping("/posts/{postId}/videos")
    public ResponseEntity<String> create(@PathVariable("postId") Long postId,
                                         @RequestPart("videoFile") MultipartFile videoFile) {
        videoService.create(postId, videoFile);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
