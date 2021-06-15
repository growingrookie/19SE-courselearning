package cn.seecoder.courselearning.po.course;

import cn.seecoder.courselearning.vo.course.CourseVO;
import cn.seecoder.courselearning.vo.course.PostVO;
import lombok.NonNull;

import java.util.Date;

public class Post {
    private Integer id;
    private Integer userId;
    private String userName;
    private String content;
    private String title;
    private Date createTime;
    private int commentCount;


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName(){return userName;}
    public void setUserName(String userName){this.userName=userName;}

    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}

    public String getContent(){return content;}
    public void setContent(String content){this.content=content;}

    public Date getCreateTime(){return createTime;}
    public void setCreateTime(Date date){this.createTime=date;}

    public int getCommentCount(){return commentCount;}
    public void setCommentCount(int commentCount){this.commentCount=commentCount;}


    public Post(@NonNull PostVO postVO){
        this.id=postVO.getId();
        this.userId=postVO.getUserId();
        this.userName=postVO.getUserName();
        this.title=postVO.getTitle();
        this.content=postVO.getContent();
        this.createTime=postVO.getCreateTime();
        this.commentCount=postVO.getCommentCount();
    }
}
