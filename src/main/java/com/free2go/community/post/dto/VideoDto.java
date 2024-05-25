package com.free2go.community.post.dto;

import com.free2go.community.post.domain.Video;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class VideoDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class VideoRes {
        private String url;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime createdAt;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime updatedAt;


        @Builder
        public VideoRes(String url, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.url = url;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public static VideoRes of(Video video) {
            return VideoRes.builder()
                    .url(video.getUrl())
                    .createdAt(video.getCreatedAt())
                    .updatedAt(video.getUpdatedAt())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class VideoCreateReq {
        private String url;

        @Builder
        public VideoCreateReq(String url) {
            this.url = url;
        }

        public Video toEntity() {
            return Video.builder()
                    .url(this.url)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class VideoDeleteReq {
        private Long id;

        @Builder
        public VideoDeleteReq(Long id) {
            this.id = id;
        }
    }

}
