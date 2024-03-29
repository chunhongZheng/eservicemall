package com.caspar.eservicemall.common.to.seckill;

import lombok.Data;

import java.math.BigDecimal;

/***
 * 秒杀订单
 * **/
@Data
public class SeckillOrderTo {

    /**
     * 订单号
     */
    private String orderSn;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀件数
     */
    private Integer num;
    /**
     * 会员id
     */
    private Long memberId;
}
