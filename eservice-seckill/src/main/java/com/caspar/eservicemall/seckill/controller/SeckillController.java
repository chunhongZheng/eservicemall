package com.caspar.eservicemall.seckill.controller;

import com.caspar.eservicemall.common.to.seckill.SeckillSkuRedisTO;
import com.caspar.eservicemall.common.utils.R;
import com.caspar.eservicemall.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * 秒杀活动场次
 * **/
@Controller
public class SeckillController {
    @Autowired
    private SeckillService seckillService;
    /**
     * 查询当前时间可以参与秒杀的商品列表
     */
    // @SentinelResource(value = "getCurrentSeckillSkus") 配置sentinel资源，默认情况下所有请求都是资源，无需配置
    @GetMapping(value = "/getCurrentSeckillSkus")
    @ResponseBody
    public R getCurrentSeckillSkus() {
        List<SeckillSkuRedisTO> results = seckillService.getCurrentSeckillSkus();
        return R.ok().setData(results);
    }
    /**
     * 根据skuId查询商品当前时间秒杀信息
     */
    @GetMapping(value = "/sku/seckill/{skuId}")
    @ResponseBody
    public R getSkuSeckilInfo(@PathVariable("skuId") Long skuId) {
        SeckillSkuRedisTO to = seckillService.getSkuSeckilInfo(skuId);
        return R.ok().setData(to);
    }


    /**
     * 秒杀商品
     * 1.校验登录状态
     * 2.校验秒杀时间
     * 3.校验随机码、场次、商品对应关系
     * 4.校验信号量扣减，校验购物数量是否限购
     * 5.校验是否重复秒杀（幂等性）【秒杀成功SETNX占位  userId_sessionId_skuId】
     * 6.扣减信号量
     * 7.发送消息，创建订单号和订单信息
     * 8.订单模块消费消息，生成订单
     * @param killId    sessionId_skuid
     * @param key   随机码
     * @param num   商品件数
     * @param model
     * @return
     */
    @GetMapping(value = "/kill")
    public String seckill(@RequestParam("killId") String killId,
                          @RequestParam("key") String key,
                          @RequestParam("num") Integer num,
                          Model model) {
        String orderSn = null;
        try {
            orderSn = seckillService.kill(killId, key, num);
            model.addAttribute("orderSn", orderSn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public static void main(String[] args) {
        HashMap<String,String> map=new HashMap<>();  //线程不安全
//    public V put(K key, V value) {
//        return putVal(hash(key), key, value, false, true);
//    }
        map.put("hashmapTest","1");
//    public synchronized V put(K key, V value) {
//        // Make sure the value is not null
//        if (value == null) {
//            throw new NullPointerException();
//        }
        Hashtable<String,String> table=new Hashtable<>(); //线程安全
        table.put("test","wqw");
    }

}
