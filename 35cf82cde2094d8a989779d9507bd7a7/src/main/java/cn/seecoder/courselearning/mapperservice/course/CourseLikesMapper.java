package cn.seecoder.courselearning.mapperservice.course;

import org.apache.ibatis.annotations.Param;

public interface CourseLikesMapper {
    int deleteByPrimaryKey(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    int insert(@Param("courseId") Integer courseId, @Param("userId") Integer userId); //多个条件进行查询时，需要加上@Param,否则查询无效 默认的话，调用mapper的insert方法之后返回的只是成功插入的行数

    int count(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    int countLikesOfCourse(Integer courseId);
}
