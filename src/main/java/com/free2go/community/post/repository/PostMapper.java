package com.free2go.community.post.repository;

import com.free2go.community.post.domain.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
    Post selectPost(@Param("id") Long id);
}
