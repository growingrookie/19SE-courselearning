package cn.seecoder.courselearning.vo.course;

import cn.seecoder.courselearning.po.course.Course;
import cn.seecoder.courselearning.po.course.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class PostVO {
    private Integer id;

    private Integer userId;

    private String userName;

    private String title;

    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private int commentCount;

    public PostVO(@NonNull Post post){
        id=post.getId();
        userId=post.getUserId();
        userName=post.getUserName();
        title=post.getTitle();
        content=post.getContent();
        createTime=post.getCreateTime();
        commentCount=post.getCommentCount();
    }


}
