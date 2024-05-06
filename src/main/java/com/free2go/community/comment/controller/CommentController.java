package com.free2go.community.comment.controller;

import com.free2go.community.comment.dto.CommentDto;
import com.free2go.community.comment.service.CommentService;
import com.free2go.community.post.service.PostService;
import com.mysql.cj.PreparedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @GetMapping("/v1/posts/comments/{id}")
    public CommentDto.CommentRes findById(@PathVariable("id") Long id) {
        return CommentDto.CommentRes.of(commentService.getCommentById(id));
    }

    @PostMapping("/v1/posts/{postId}/comments")
    public void save(@PathVariable("postId") Long postId, @RequestBody final CommentDto.CommentReq req) {
        commentService.saveComment(postId, req);
    }

    @PutMapping("/v1/posts/comments/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody final CommentDto.CommentReq req) {
        commentService.updateComment(id, req);
    }

    @DeleteMapping("/v1/posts/comments/{id}")
    public void delete(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }
}
