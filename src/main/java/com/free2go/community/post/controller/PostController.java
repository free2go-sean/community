package com.free2go.community.post.controller;

import com.free2go.community.post.dto.PostDto;
import com.free2go.community.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/v0/posts/{id}")
    public PostDto.PostRes selectPosts(@PathVariable("id") Long id) {
        return PostDto.PostRes.of(postService.selectPosts(id));
    }

    @GetMapping("/v1/posts/{id}")
    public PostDto.PostRes getPosts(@PathVariable("id") Long id) throws Exception {
        return postService.getPostsById(id);
    }

    @PostMapping("/v1/posts")
    public void savePosts(@RequestBody PostDto.PostReq req) {
        postService.savePosts(req);
    }
}
