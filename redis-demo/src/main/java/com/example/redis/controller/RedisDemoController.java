package com.example.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisDemoController {
    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping(value = "/redisDemo")
    @ResponseBody
    public String  redisDemo(){
          redisTemplate.opsForValue().set("redisdemo:esgd","redisd");

          return "hello redis deomo";
    }

}
