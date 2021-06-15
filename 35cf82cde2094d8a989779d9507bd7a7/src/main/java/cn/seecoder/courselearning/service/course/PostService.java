package cn.seecoder.courselearning.service.course;

import cn.seecoder.courselearning.po.course.Post;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.course.PostVO;
import java.util.List;
public interface PostService {
    List<PostVO> getReleasedPosts(Integer uid);
    PostVO getPost(Integer postId, Integer uid);
    Post getByPrimaryKey(Integer postId);
    ResultVO<PostVO> createPost(PostVO postVO);
    ResultVO<String> delPost(Integer postId);

}
