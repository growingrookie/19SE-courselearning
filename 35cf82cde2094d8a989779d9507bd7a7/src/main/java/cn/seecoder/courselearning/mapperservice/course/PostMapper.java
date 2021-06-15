package cn.seecoder.courselearning.mapperservice.course;

import cn.seecoder.courselearning.po.course.Post;

import java.util.List;

public interface PostMapper {
    List<Post> selectByUserId(Integer userId);
    Post selectByPrimaryKey(Integer postId);
    int insert(Post post);
    int delete(Post post);
    int deleteByPrimaryKey(Integer postId);
}

