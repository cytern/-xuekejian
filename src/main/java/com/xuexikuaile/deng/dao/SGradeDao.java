package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.SGrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SGradeDao {
    int deleteByPrimaryKey(Integer sGradeId);

    int insert(SGrade record);

    int insertSelective(SGrade record);

    SGrade selectByPrimaryKey(Integer sGradeId);

    int updateByPrimaryKeySelective(SGrade record);

    int updateByPrimaryKey(SGrade record);

    List<SGrade> getAllGrade(Integer studentId);

    List<SGrade> getAllGradeByTestId(Integer testId);

    List<SGrade> getByTestClassId(@Param(value = "testId")Integer testId,
                                  @Param(value = "classId")Integer classId);

    SGrade selectByStudentIdAndTestId(@Param(value = "testId")Integer testId,
                                      @Param(value = "studentId")Integer studentId);


    List<SGrade> getByStudentId(Integer studentId);


}