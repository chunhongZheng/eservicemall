package com.example.redis.lock;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
@Component
public class RedisLockDemo {
    @Autowired
    StringRedisTemplate redisTemplate;
    String  REDIS_LOCK_KEY="redislock";

    // 没有加锁的版本
    public void demo1() {
        //
        System.out.println("处理业务逻辑");
    }
    //   /**
    //     * 单机环境下  使用Synchronized或lock来实现
    //     * 但是在分布式环境下 因为竞争的线程可能不在同一个节点(不是同一个JVM) 所以许需要一个让所有进程都能访问到的锁来实现
    //     * 比如redis或者zookeeper来实现
    //     * <p>
    //     * 不同进程jvm层面的锁就不管用了，那么可以利用第三方的一个组件，来获取锁，未获取到锁，则阻塞当前想要运行的线程

    public void demo2() {
        Lock lock = new ReentrantLock();

        if (lock.tryLock()) {
            System.out.println("加锁成功");
            //处理业务逻辑
        } else {
            System.out.println("加锁失败");
        }
    }


    /**
     * * 问题 如果部署的微服务jar包的机器挂了，代码没有走到finllay里面释放锁，
     * * 那么这个key就没有被删除 永远留在了redis服务器种  所有需要加一个过期时间
     * ***/

    public void demo3(StringRedisTemplate redisTemplate) {
        String value = UUID.randomUUID().toString() + Thread.currentThread().getName();
        //setnx
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value);
        if (!aBoolean) {
            System.out.println("抢锁失败");
        } else {
            try {
                System.out.println("处理业务逻辑");
            } finally {
                //释放锁
                redisTemplate.delete(REDIS_LOCK_KEY);
            }
        }
    }

    /**
     * setIfAbsent 和 expire 两个命令非原子性的
     * 所以需要使用一条命令
     */
    public void demo4(StringRedisTemplate redisTemplate) {
        String value = UUID.randomUUID().toString() + Thread.currentThread().getName();
        //setnx
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value);
        redisTemplate.expire(REDIS_LOCK_KEY, 10L, TimeUnit.SECONDS);
        if (!aBoolean) {
            System.out.println("抢锁失败");
        } else {
            try {
                //sellGoods();
                System.out.println("处理业务逻辑");
            } finally {
                //释放锁
                redisTemplate.delete(REDIS_LOCK_KEY);
            }
        }
    }
    /**
     * 这个时候感觉没有问题了
     * 如果业务线程A处理业务时间太长 redis的lock过期被删除
     * 线程B进入 此时线程A处理完成 删除了线程B的锁
     * 只能删除自己创建的锁 修改见demo6
     */
    public void demo5(StringRedisTemplate redisTemplate) {
        String value = UUID.randomUUID().toString() + Thread.currentThread().getName();
        //setnx
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value, 10L, TimeUnit.MINUTES);
        if (!aBoolean) {
            System.out.println("抢锁失败");
        } else {
            try {
              //  sellGoods();
                System.out.println("处理业务逻辑");
            } finally {
                //释放锁
                redisTemplate.delete(REDIS_LOCK_KEY);
            }
        }
    }

    /**
     * 这个时候还有一个问题：
     * finally种 redis的get和delete非原子性的
     * 解决方案：1.使用Lua脚本
     * 2.用redis事务
     */
    public void demo6(StringRedisTemplate redisTemplate) {
        String value = UUID.randomUUID().toString() + Thread.currentThread().getName();
        //setnx
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, value, 10L, TimeUnit.MINUTES);
        if (!aBoolean) {
            System.out.println("抢锁失败");
        } else {
            try {
               // sellGoods();
                System.out.println("处理业务逻辑");
            } finally {
                //释放锁
                if (value.equalsIgnoreCase((String) redisTemplate.opsForValue().get(REDIS_LOCK_KEY))) {
                    redisTemplate.delete(REDIS_LOCK_KEY);
                }
            }
        }
    }


    /**
     * 使用lua脚本
     * **/
    public void demo7(){

    }
}
