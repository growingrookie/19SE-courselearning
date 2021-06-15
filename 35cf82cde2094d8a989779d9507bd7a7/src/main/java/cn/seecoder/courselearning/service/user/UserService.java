package cn.seecoder.courselearning.service.user;

import cn.seecoder.courselearning.po.user.User;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.coupon.CouponVO;
import cn.seecoder.courselearning.vo.user.UserVO;

import java.util.List;


public interface UserService {
    // 用户注册
    ResultVO<UserVO> userRegister(UserVO user);
    // 用户登录验证
    ResultVO<UserVO> userLogin(String phone, String password);
    // 根据id查找用户
    UserVO getUser(Integer uid);
    // 查看该用户购买某课程可用的所有优惠
    ResultVO<UserVO> getAllAvailableCouponsForOrder(Integer orderId);

    void increaseBalance(Integer id, Integer delta);

    void decreaseBalance(Integer id, Integer delta);

    List<User> getAll();
}
