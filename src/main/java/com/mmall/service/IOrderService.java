package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * Author: HANZ
 * Date: 2019/1/22
 * Time: 22:01
 */
public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);
}
