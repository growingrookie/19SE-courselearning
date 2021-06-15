package cn.seecoder.courselearning.controller.course;

import cn.seecoder.courselearning.service.course.PostService;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.course.PostVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/discussion")
public class DiscussionPostController {
    @Resource
    private PostService postService;

    @PostMapping("/create")
    public ResultVO<PostVO> creatPost(@RequestBody PostVO post) {
        return postService.createPost(post);
    }

    @PostMapping("/delete/{postId}")
    public ResultVO<String> deletePost(@PathVariable Integer postId) {
        return postService.delPost(postId);
    }

}
