package springboot.canal.demo.controller;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: dada
 * @date: 2020/11/3 19:18
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/getUserById")
    @ResponseBody
    public JSONObject getUserById(String userId){

        JSONObject user = (JSONObject) redisTemplate.opsForHash().get("user", userId);
        return user;
    }

}
