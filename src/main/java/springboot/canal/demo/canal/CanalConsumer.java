package springboot.canal.demo.canal;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: dada
 * @date: 2020/11/3 14:10
 */
@Component
@RocketMQMessageListener(consumerGroup = "group", topic = "${rocketmq.producer.topic}", consumeMode = ConsumeMode.ORDERLY, consumeThreadMax = 1)
public class CanalConsumer implements RocketMQListener<String> {

    @Autowired
    private RedisTemplate redisTemplate;

    @SneakyThrows
    @Override
    public void onMessage(String msg) {
//        System.out.println(msg);

        if (!JSONUtil.isJson(msg)) {
            return;
        }

        JSONObject msgJsonObject = JSONUtil.parseObj(msg);
        String sqlType = msgJsonObject.getStr("type");
        switch (sqlType) {
            case "UPDATE":
            case "INSERT":
            case "DELETE":
                break;
            default:
                System.out.println("不同步的消息类型：" + sqlType);
                return;
        }

//        System.out.println(msgJsonObject.toStringPretty());

        String table = msgJsonObject.getStr("table");
        JSONArray data = msgJsonObject.getJSONArray("data");
        JSONArray pkNames = msgJsonObject.getJSONArray("pkNames");

        System.out.println("线程名称" + Thread.currentThread().getName() + ",表名为:" + msgJsonObject.getStr("table") + ",sql 类型为：" + sqlType);
        if ("UPDATE".equals(sqlType) || "INSERT".equals(sqlType)) {

            for (int i = 0; i < data.size(); i++) {
                JSONObject object = data.getJSONObject(i);
                String key = object.getStr(pkNames.getStr(i), "0");
                redisTemplate.opsForHash().put(table, key, object);
            }

            return;
        }
        if ("DELETE".equals(sqlType)) {
            for (int i = 0; i < data.size(); i++) {
                JSONObject object = data.getJSONObject(i);
                String key = object.getStr(pkNames.getStr(i), "0");
                redisTemplate.opsForHash().delete(table, key);
            }
        }
    }
}
