package com.caspar.eservicemall.common.to.seckill;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 给Redis中存放的skuInfo的信息
 * @Created: with IntelliJ IDEA.
 * @author: wanzenghui
 * @createTime: 2020-07-09 21:39
 **/

@Data
public class SeckillSkuRedisTO {
    /**
     * 活动id
     */
    private Long promotionId;
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
     * 秒杀总量
     */
    private BigDecimal seckillCount;
    /**
     * 每人限购数量
     */
    private BigDecimal seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;

    //sku的详细信息
    private SkuInfoTO skuInfo;

    //当前商品秒杀的开始时间
    private Long startTime;

    //当前商品秒杀的结束时间
    private Long endTime;

    //当前商品秒杀的随机码
    private String randomCode;
}
