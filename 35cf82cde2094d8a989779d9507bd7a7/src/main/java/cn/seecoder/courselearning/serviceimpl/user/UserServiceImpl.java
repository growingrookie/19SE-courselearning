package cn.seecoder.courselearning.serviceimpl.user;

import cn.seecoder.courselearning.mapperservice.user.UserMapper;
import cn.seecoder.courselearning.po.coupon.Coupon;
import cn.seecoder.courselearning.po.course.Course;
import cn.seecoder.courselearning.po.order.CourseOrder;
import cn.seecoder.courselearning.po.user.User;
import cn.seecoder.courselearning.service.user.UserService;
import cn.seecoder.courselearning.util.Constant;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.coupon.CouponVO;
import cn.seecoder.courselearning.vo.order.CourseOrderVO;
import cn.seecoder.courselearning.vo.user.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import cn.seecoder.courselearning.dto.coupon.CouponDTO;
import cn.seecoder.courselearning.dto.coupon.DeliverCouponDTO;
import cn.seecoder.courselearning.mapperservice.coupon.CouponMapper;
import cn.seecoder.courselearning.mapperservice.coupon.UserCouponMapper;
import cn.seecoder.courselearning.po.coupon.Coupon;
import cn.seecoder.courselearning.po.coupon.UserCoupon;
import cn.seecoder.courselearning.po.course.Course;
import cn.seecoder.courselearning.po.order.CourseOrder;
import cn.seecoder.courselearning.po.user.User;
import cn.seecoder.courselearning.service.coupon.coupondeliverstrategy.CouponDeliverStrategy;
import cn.seecoder.courselearning.service.coupon.CouponService;
import cn.seecoder.courselearning.service.course.CourseService;
import cn.seecoder.courselearning.service.order.QueryOrderService;
import cn.seecoder.courselearning.service.user.UserService;
import cn.seecoder.courselearning.util.Constant;
import cn.seecoder.courselearning.util.CouponValidator;
import cn.seecoder.courselearning.vo.coupon.CouponVO;
import cn.seecoder.courselearning.vo.order.CourseOrderVO;
import cn.seecoder.courselearning.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public ResultVO<UserVO> userRegister(UserVO vo) {
        String phone = vo.getPhone();
        String uname = vo.getUname();
        String password = vo.getPassword();
        if(userMapper.selectByPhone(phone) == null){
            if(StringUtils.hasText(uname) && StringUtils.hasText(password)){
                vo.setCreateTime(new Date());
                vo.setBalance(0);
                User user = new User(vo);
                userMapper.insert(user);
                System.out.println(new UserVO(user));
                return new ResultVO<>(Constant.REQUEST_SUCCESS, "账号注册成功！", new UserVO(user));
            }else{
                return new ResultVO<>(Constant.REQUEST_FAIL, "用户名或密码不得为空！");
            }
        }
        return new ResultVO<>(Constant.REQUEST_FAIL, "这个手机号已经注册过账号。");
    }

    @Override
    public ResultVO<UserVO> userLogin(String phone, String password) {
        User user = userMapper.selectByPhone(phone);
        if(user == null){
            return new ResultVO<>(Constant.REQUEST_FAIL, "这个手机号尚未注册过账号。");
        }else{
            if(!user.getPassword().equals(password))
                return new ResultVO<>(Constant.REQUEST_FAIL, "账号或密码错误！");
        }
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "账号登陆成功！", new UserVO(userMapper.selectByPhone(phone)));
    }

    @Override
    public UserVO getUser(Integer uid) {
        User userFromDB = userMapper.selectByPrimaryKey(uid);
        if(userFromDB == null){
            return new UserVO();
        }else{
            return new UserVO(userFromDB);
        }
    }

    @Override
    public void increaseBalance(Integer id, Integer delta) {
        userMapper.increaseBalance(id, delta);
    }

    @Override
    public void decreaseBalance(Integer id, Integer delta) {
        userMapper.decreaseBalance(id, delta);
    }

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Resource
    private CouponMapper couponMapper;

    @Resource
    private UserCouponMapper userCouponMapper;

    private UserService userService;

    private CourseService courseService;

    private QueryOrderService orderService;

    @Override
    public ResultVO<UserVO> getAllAvailableCouponsForOrder(Integer orderId) {
        CourseOrder courseOrder = orderService.getByPrimaryKey(orderId);
        if (orderId == null) {
            return new ResultVO<UserVO>(Constant.REQUEST_FAIL, "查询失败，订单不存在！", (UserVO) Collections.EMPTY_LIST);
        }
        CourseOrderVO orderVO = new CourseOrderVO(courseOrder);
        // 获取已经使用的优惠券
        List<Coupon> usedCoupons = couponMapper.selectByOrderId(orderVO.getId());
        boolean sharable = true;
        // 获取对应的优惠券
        for (Coupon usedCoupon : usedCoupons) {
            if (!usedCoupon.isSharable()) {
                sharable = false;
                break;
            }
        }
        if (sharable) {
            // 查询可用优惠券
            List<Coupon> userCoupons = couponMapper.selectByUserId(orderVO.getUserId());
            List<Coupon> availableCoupons = new ArrayList<>();
            Course course = courseService.getByPrimaryKey(orderVO.getCourseId());
            for (Coupon coupon : userCoupons) {
                if (coupon.getType().getCouponStrategy().canUse(orderVO, coupon) && coupon.getScope().canUse(course, coupon)) {
                    // 再去除当前订单使用的
                    Coupon usedCoupon = usedCoupons.stream().filter(used -> used.getId().equals(coupon.getId())).findFirst().orElse(null);
                    if (usedCoupon == null) {
                        availableCoupons.add(coupon);
                    }
                }
            }
            return new ResultVO<UserVO>(Constant.REQUEST_SUCCESS, "查询成功", (UserVO) Collections.EMPTY_LIST);
        }
        return new ResultVO<UserVO>(Constant.REQUEST_SUCCESS, "查询成功", (UserVO) Collections.EMPTY_LIST);
    }
}
