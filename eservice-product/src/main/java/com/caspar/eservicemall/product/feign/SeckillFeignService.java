package com.caspar.eservicemall.product.feign;

import com.caspar.eservicemall.common.utils.R;
import com.caspar.eservicemall.product.fallback.SeckillFeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient("eservice-seckill")
@FeignClient(value = "eservice-seckill", fallback = SeckillFeignServiceFallBack.class,
        configuration = SeckillFeignServiceFallBack.class)
//远程调用失败返回的配置类 fallback = SeckillFeignServiceFallBack.class
public interface SeckillFeignService {


    /**
     * 根据skuId查询商品是否参加秒杀活动
     */
    @GetMapping(value = "/sku/seckill/{skuId}")
    R getSkuSeckilInfo(@PathVariable("skuId") Long skuId);

}
