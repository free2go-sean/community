package com.free2go.community.comment.service;

import com.free2go.community.comment.domain.Comment;
import com.free2go.community.comment.dto.CommentDto;
import com.free2go.community.comment.repository.CommentRepository;
import com.free2go.community.common.exception.EntityNotFoundException;
import com.free2go.community.post.domain.Post;
import com.free2go.community.post.dto.PostDto;
import com.free2go.community.post.repository.PostRepository;
import com.free2go.community.post.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;


    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Transactional
    public void saveComment(Long postId, CommentDto.CommentReq req) {
        Post post = postService.getPostsById(postId);

        Comment comment = req.toEntity();
        comment.setPost(post);

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long id, CommentDto.CommentReq req) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        comment.update(req.getComment());
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.delete(getCommentById(id));
    }
}
