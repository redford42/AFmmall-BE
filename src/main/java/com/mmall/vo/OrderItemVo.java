package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: HANZ
 * Date: 2019/3/30
 * Time: 14:39
 */
@Data
public class OrderItemVo {
    private Long orderNo;
    private Integer productId;
    private String productName;
    private String productImage;
    private BigDecimal currentUnitPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String createTime;

}
