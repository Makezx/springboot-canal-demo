package springboot.canal.demo.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springboot.canal.demo.entity.User;

/**
 * mqcontroller
 *
 * @description:
 * @author: dada
 * @date: 2020/11/4 20:09
 */
@RestController
@RequestMapping("/rocketMq")
public class RocketMqController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    // 发送-普通消息: rocketMQ
    @RequestMapping("sendMsg")
    @ResponseBody
    public String sendMsg(String userId, String topic) {

        JSONObject jsonObject = (JSONObject) redisTemplate.opsForHash().get("user", userId);
        User user = JSONUtil.toBean(jsonObject, User.class);

        System.out.println("发送前参数: " + user.toString());

        rocketMQTemplate.convertAndSend(topic, user);

        return "发送事物消息1";
    }

    // 发送-事物消息: rocketMQ
    @RequestMapping("transaction1")
    @ResponseBody
    public String transaction1(String userId) {

        JSONObject jsonObject = (JSONObject) redisTemplate.opsForHash().get("user", userId);
        User user = JSONUtil.toBean(jsonObject, User.class);

        System.out.println("发送前参数: " + user.toString());

        rocketMQTemplate.sendMessageInTransaction(
                // 主题
                "transaction1",
                // 消息体
                MessageBuilder
                        .withPayload(user)
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID())
                        .build(), null
        );

        return "发送事物消息1";
    }

    // 发送-事物消息: rocketMQ
    @RequestMapping("transaction2")
    @ResponseBody
    public String transaction2(String userId) {

        JSONObject jsonObject = (JSONObject) redisTemplate.opsForHash().get("user", userId);
        User user = JSONUtil.toBean(jsonObject, User.class);

        System.out.println("发送前参数: " + user.toString());

        rocketMQTemplate.sendMessageInTransaction(
                // 主题
                "transaction2",
                // 消息体
                MessageBuilder
                        .withPayload(user)
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID())
                        .build(), null
        );

        return "发送事物消息2";
    }

}
