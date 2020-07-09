package com.xuexikuaile.deng.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @Author: cytern
 * @Date: 2020/6/24 17:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalScience {
    private BigDecimal totalScience;
    private BigDecimal totalArt;
    private int studentId;
    private int classId;
    private int testId;
    private int SGradeId;
    private BigDecimal totalPrice;
}
