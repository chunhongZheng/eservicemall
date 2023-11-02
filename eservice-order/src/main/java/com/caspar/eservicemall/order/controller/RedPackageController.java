package com.caspar.eservicemall.order.controller;

import com.alibaba.csp.sentinel.util.IdUtil;
import com.google.common.primitives.Ints;
import com.mysql.cj.util.TimeUtil;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("order/rebPackage")
public class RedPackageController {
    private static final String RED_PACKAGE_KEY = "RedPackage";
    private static final Object RED_PACKAGE_CONSUME_KEY = "RedPackage:consume";
    @Autowired
    RedisTemplate redisTemplate;
   //发红包
    @RequestMapping(value ="/sendRedPackage")
    public String sendRedPackage(int totalMoney, int redPackageNumber){
       //1：拆红包, 将总金额totalMoney拆分为redPackageNumber个子红包
        int[] splitRedPackages=splitRedPackageAlgorithm(totalMoney,redPackageNumber);//拆分红包算法通过后获得的多个子红包
       //2： 发红包并保存进redis list结构里面且设置过期时间
        String key= RED_PACKAGE_KEY+ UUID.randomUUID();
        redisTemplate.opsForList().leftPushAll(key, splitRedPackages);
        redisTemplate.expire(key,1, TimeUnit.DAYS);
        // 3 发红包 ok,返回前台显示
        return key + "\t" + Ints.asList(Arrays.stream(splitRedPackages).map(Integer::valueOf).toArray());
    }


    //抢红包
    @RequestMapping(value ="/robRedPackage")
    public String robRedPackage(String redPackageKey,String userId){
        // 1 验证某个用户是否抢过红包，不可以多抢
        Object redPackage = redisTemplate.opsForHash().get(RED_PACKAGE_CONSUME_KEY+redPackageKey,userId);
        // 2 没有抢过可以去抢红包，否则返回-2 表示该用户抢过红包了
        if(null==redPackage){
            // 2.1 从大红包(list)里面出队一个作为该客户抢的红包，抢到了一个红包
            Object partRedPackage = redisTemplate.opsForList().leftPop(RED_PACKAGE_KEY+redPackageKey);
            System.out.println("用户:"+userId+"\t  抢到了多少钱的红包" + partRedPackage);

            return String.valueOf(partRedPackage);
        }
        // 抢完了
        return "errorCode:-1,红包抢完了";
    }




    //拆红包算法
    private int[] splitRedPackageAlgorithm(int totalMoney, int redPackageNumber) {
        int[] redPackageNumbers=new int[redPackageNumber];
        int useMoney=0;
        for (int i=0;i<redPackageNumber;i++){
            if(i==redPackageNumber-1){
                //最后一个红包
                redPackageNumbers[i]=totalMoney- useMoney;
            }else{
               //二倍均值法  剩余红包金额为M， 剩余人数为N，那么有如下公式：
                //每次抢到的金额 = 随机区间（0，（剩余红包金额M /剩余人数N）*2）
                int avgMoney= ((totalMoney-useMoney)/(redPackageNumber-i))*2;
                redPackageNumbers[i]=1 + new Random().nextInt(avgMoney-1);
            }
            useMoney=useMoney+redPackageNumbers[i];;
        }
        return redPackageNumbers;
    }
}
