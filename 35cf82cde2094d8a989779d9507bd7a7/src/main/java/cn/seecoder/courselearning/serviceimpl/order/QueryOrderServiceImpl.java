package cn.seecoder.courselearning.serviceimpl.order;

import cn.seecoder.courselearning.mapperservice.order.CourseOrderMapper;
import cn.seecoder.courselearning.po.order.CourseOrder;
import cn.seecoder.courselearning.service.order.QueryOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class QueryOrderServiceImpl implements QueryOrderService {
    @Resource
    private CourseOrderMapper courseOrderMapper;

    @Override
    public CourseOrder queryMostRecentOrder(Integer uid, Integer courseId) {
//        List<CourseOrder> CourseOrderList = courseOrderMapper.selectByUserId(uid);
//        Date mostRecentDate = CourseOrderList.get(0).getCreateTime();
//        CourseOrder courseOrderTemp = CourseOrderList.get(0);
//        for (CourseOrder courseOrder : CourseOrderList) {
//            if ((courseOrder.getCreateTime()).compareTo(mostRecentDate) > 0) {
//                mostRecentDate = courseOrder.getCreateTime();
//                courseOrderTemp = courseOrder;
//            }
//        }
//        return courseOrderTemp;
        return null;
    }

    @Override
    public CourseOrder getByPrimaryKey(Integer orderId) {
        return null;
        //return courseOrderMapper.selectByPrimaryKey(orderId);
    }
}
