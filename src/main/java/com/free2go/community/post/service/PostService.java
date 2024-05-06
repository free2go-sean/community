package com.free2go.community.post.service;

import com.free2go.community.common.exception.EntityNotFoundException;
import com.free2go.community.post.domain.Post;
import com.free2go.community.post.dto.PostDto;
import com.free2go.community.post.repository.PostMapper;
import com.free2go.community.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Post getPostsById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public Post selectPosts(Long id) {
        return postMapper.selectPost(id);
    }

    public void savePosts(PostDto.PostReq req) {
        postRepository.save(req.toEntity());
    }
}
