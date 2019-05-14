package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author: HANZ
 * Date: 2019/3/30
 * Time: 14:37
 */
@Data
public class OrderVo {
    private Long orderNo;
    private BigDecimal payment;
    private Integer paymentType;
    private String paymentTypeDesc;
    private Integer postage;
    private Integer status;
    private String statusDesc;

    private String paymentTime;

    private String sendTime;

    private String endTime;

    private String closeTime;

    private String createTime;

    //订单明细
    private List<OrderItemVo> orderItemVoList;

    private String imageHost;
    private Integer shippingId;
    private String receiverName;

    private ShippingVo shippingVo;


}
