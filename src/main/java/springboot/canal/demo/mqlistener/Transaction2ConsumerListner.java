package springboot.canal.demo.mqlistener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import springboot.canal.demo.entity.User;

//@Component
//@RocketMQMessageListener(topic = "example", consumerGroup = "transaction2")
public class Transaction2ConsumerListner implements RocketMQListener<User> {

    @Override
    public void onMessage(User message) {
        System.out.println("事务消费接收2：" + message);
    }

}