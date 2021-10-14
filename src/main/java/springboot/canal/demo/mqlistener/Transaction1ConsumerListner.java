package springboot.canal.demo.mqlistener;

import cn.hutool.json.JSONObject;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import springboot.canal.demo.entity.User;

@Component
@RocketMQMessageListener(topic = "example", consumerGroup = "transaction1")
public class Transaction1ConsumerListner implements RocketMQListener<JSONObject> {

    @Override
    public void onMessage(JSONObject jsonObject) {
        System.out.println("事务消费接收1：" + jsonObject);
    }

}