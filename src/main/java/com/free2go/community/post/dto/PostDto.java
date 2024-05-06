package com.free2go.community.post.dto;

import com.free2go.community.comment.dto.CommentDto;
import com.free2go.community.post.domain.Post;
import lombok.*;

public class PostDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostRes {
        private Long id;
        private String title;
        private String contents;

        @Builder
        public PostRes(Long id, String title, String contents) {
            this.id = id;
            this.title = title;
            this.contents = contents;
        }

        public static PostRes of(Post post) {
            return PostRes.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostReq {
        private String title;
        private String contents;

        @Builder
        public PostReq(String title, String contents) {
            this.title = title;
            this.contents = contents;
        }

        public Post toEntity() {
            return Post.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .build();
        }
    }
}
