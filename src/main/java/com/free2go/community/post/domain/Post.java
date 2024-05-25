package com.free2go.community.post.domain;

import com.free2go.community.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "POST")
public class Post extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Video> videos = new ArrayList<>();

    @Builder
    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Post update(String title, String contents) {
        this.title = title;
        this.contents = contents;

        return this;
    }

    public void addVideo(Video video) {
        this.videos.add(video);
    }

    public void removeVideo(Video video) {
        this.videos.remove(video);
    }

    public void removeAllVideos(List<Video> videos) {
        this.videos.removeAll(videos);
    }


}
