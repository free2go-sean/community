package com.free2go.community.comment.domain;


import com.free2go.community.common.jpa.BaseEntity;
import com.free2go.community.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "COMMENT")
public class Comment extends BaseEntity {
    @Column(nullable = false, length = 4000)
    private String comment;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id"
                , nullable = false
                , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Post post;

    @Builder
    public Comment(String comment) {
        this.comment = comment;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}
