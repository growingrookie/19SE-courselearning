package cn.seecoder.courselearning.serviceimpl.course;

import cn.seecoder.courselearning.mapperservice.course.CourseMapper;
import cn.seecoder.courselearning.mapperservice.course.PostMapper;
import cn.seecoder.courselearning.po.course.Course;
import cn.seecoder.courselearning.po.course.Post;
import cn.seecoder.courselearning.service.course.PostService;
import cn.seecoder.courselearning.util.Constant;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.course.CourseVO;
import cn.seecoder.courselearning.vo.course.PostVO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;
    @Override
    public List<PostVO> getReleasedPosts(Integer uid){
        List<PostVO> ret = new ArrayList<>();
        List<Post> postList = postMapper.selectByUserId(uid);
        for(Post post: postList){
            ret.add(new PostVO(post));
        }
        return ret;
    }

    @Override
    public PostVO getPost(Integer postId, Integer uid) {
       Post post=getByPrimaryKey(postId);
       return new PostVO(post);
    }

    @Override
    public Post getByPrimaryKey(Integer postId) {
        return postMapper.selectByPrimaryKey(postId);
    }

    @Override
    public ResultVO<PostVO> createPost(PostVO postVO) {
        postVO.setCreateTime(new Date());
        Post post = new Post(postVO);
        if(postMapper.insert(post) > 0){
            return new ResultVO<PostVO>(Constant.REQUEST_SUCCESS, "课程创建成功。", new PostVO(post));
        }
        return new ResultVO<>(Constant.REQUEST_FAIL, "服务器错误");
    }

    @Override
    public ResultVO<String> delPost(Integer postId) {
        if(postMapper.selectByPrimaryKey(postId )== null)
            return new ResultVO<>(Constant.REQUEST_FAIL, "帖子不存在！");
        else
            postMapper.deleteByPrimaryKey(postId);
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "删帖成功");
    }
}
