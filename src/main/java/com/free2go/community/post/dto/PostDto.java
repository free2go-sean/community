package com.free2go.community.post.dto;

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
        public PostRes(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.contents = post.getContents();
        }

        public static PostRes of(Post post) {
            return PostRes.builder()
                    .post(post)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostCreateReq {
        private String title;
        private String contents;

        @Builder
        public PostCreateReq(String title, String contents) {
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

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostUpdateReq {
        private String title;
        private String contents;

        @Builder
        public PostUpdateReq(String title, String contents) {
            this.title = title;
            this.contents = contents;
        }
    }
}
