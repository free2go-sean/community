package com.free2go.community.post.service;

import com.free2go.community.post.domain.Post;
import com.free2go.community.post.domain.Video;
import com.free2go.community.post.dto.VideoDto;
import com.free2go.community.post.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final PostService postService;

    @Value("${community.upload-path.video}")
    private String UPLOAD_PATH;

    public Video findById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));
    }

    @Transactional
    public Video create(Long postId, MultipartFile videoFile) {
        // TODO : 파일첨부 모듈화 하기
        Post post = postService.findById(postId);
        String url = UPLOAD_PATH + UUID.randomUUID() + videoFile.getOriginalFilename().split(".")[1];

        File destination = new File(url);
        try {
            videoFile.transferTo(destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        VideoDto.VideoCreateReq req = VideoDto.VideoCreateReq.builder()
                                                            .url(url)
                                                            .build();

        Video video = videoRepository.save(req.toEntity());
        post.addVideo(video);

        return video;
    }

    public void delete(Long postId, Long videoId) {
        Post post = postService.findById(postId);
        Video video = post.getVideos().stream().filter(v -> v.getId().equals(videoId)).findFirst().get();

        post.removeVideo(video);
        videoRepository.delete(video);
    }

    public void deleteAllByPostId(Long postId) {
        Post post = postService.findById(postId);

        List<Video> videos = post.getVideos();
        videoRepository.deleteAll(videos);
    }
}
