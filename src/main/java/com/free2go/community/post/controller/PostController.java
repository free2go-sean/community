package com.free2go.community.post.controller;

import com.free2go.community.post.domain.Post;
import com.free2go.community.post.dto.PostDto;
import com.free2go.community.post.service.PostService;
import com.free2go.community.post.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final VideoService videoService;

    @GetMapping("/v1/posts/{id}")
    public PostDto.PostRes getPosts(@PathVariable("id") Long id) {
        return PostDto.PostRes.of(postService.findById(id));
    }

    @PostMapping("/v1/posts")
    public ResponseEntity<String> create(@RequestPart("data") final PostDto.PostCreateReq req,
                                         @RequestPart("video") final MultipartFile videoFile) {
        Post post = postService.create(req);

        if (!ObjectUtils.isEmpty(videoFile)) {
            videoService.create(post.getId(), videoFile);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/v1/posts/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody final PostDto.PostUpdateReq req) {
        postService.update(id, req);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/v1/posts/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        postService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
