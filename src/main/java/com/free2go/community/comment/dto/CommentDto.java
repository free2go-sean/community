package com.free2go.community.comment.dto;

import com.free2go.community.comment.domain.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CommentRes {
        private Long id;
        private String comment;

        @Builder
        public CommentRes(Long id, String comment) {
            this.id = id;
            this.comment = comment;
        }

        public static CommentRes of(Comment comment) {
            return CommentRes.builder()
                    .id(comment.getId())
                    .comment(comment.getComment())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CommentReq {
        private String comment;

        @Builder
        public CommentReq(String comment) {
            this.comment = comment;
        }

        public Comment toEntity() {
            return Comment.builder()
                    .comment(this.comment)
                    .build();
        }
    }
}
