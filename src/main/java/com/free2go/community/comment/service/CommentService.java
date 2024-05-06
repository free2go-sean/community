package com.free2go.community.comment.service;

import com.free2go.community.comment.domain.Comment;
import com.free2go.community.comment.dto.CommentDto;
import com.free2go.community.comment.repository.CommentRepository;
import com.free2go.community.post.domain.Post;
import com.free2go.community.post.dto.PostDto;
import com.free2go.community.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public CommentDto.CommentRes getCommentById(Long id) throws Exception {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new Exception("data not found"));

        return CommentDto.CommentRes.of(comment);
    }

    @Transactional
    public void saveComment(Long postId, CommentDto.CommentReq req) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("data not found"));

        Comment comment = req.toEntity();
        comment.setPost(post);

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long id, CommentDto.CommentReq req) throws Exception {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new Exception("data not found"));

        comment.update(req.getComment());
    }

    @Transactional
    public void deleteComment(Long id) throws Exception {
        commentRepository.delete(
                commentRepository.findById(id).orElseThrow(() -> new Exception("data not found"))
        );
    }
}
