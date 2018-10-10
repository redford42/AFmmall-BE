package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Hanz
 * @date 2018/10/7
 */
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    public ServerResponse add(Integer userId, Integer productId, Integer count) {
        Cart cart = cartMapper.selectByUserIdProductId(userId, productId);
        if (cart == null) {
            //这个产品不在这个购物车里，需要新建一个这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartMapper.insert(cartItem);
        } else {
            //这个产品已经在购物车里了
            //如果这个产品已存在，则数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }
        //要封装valueObject来返回图片给前端
        return null;
    }

    private CartVo getCartVoLimit(Integer userId) {
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cartItem : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(cartItem.getUserId());
                cartProductVo.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null){
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCunt = 0;
                    if (product.getStock() >= cartItem.getQuantity()){
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else{
                        buyLimitCunt = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                    }

                }
            }
        }
        return cartVo;
    }
}
