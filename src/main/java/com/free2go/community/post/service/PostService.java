package com.free2go.community.post.service;

import com.free2go.community.post.domain.Post;
import com.free2go.community.post.dto.PostDto;
import com.free2go.community.post.repository.PostMapper;
import com.free2go.community.post.repository.PostRepository;
import com.free2go.community.util.BeanUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;


    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("data not found"));
    }

    @Transactional
    public Post create(PostDto.PostCreateReq req) {
        return postRepository.save(req.toEntity());
    }

    @Transactional
    public Post update(Long postId, PostDto.PostUpdateReq req) {
        Post post = this.findById(postId);

        return post.update(req.getTitle(), req.getContents());
    }

    @Transactional
    public void delete(Long postId) {
        Post post = this.findById(postId);

        if (!ObjectUtils.isEmpty(post.getVideos())) {
            VideoService videoService = (VideoService) BeanUtil.getBean(VideoService.class);

            post.removeAllVideos(post.getVideos());
            videoService.deleteAllByPostId(post.getId());
        }

        postRepository.delete(post);
    }
}
