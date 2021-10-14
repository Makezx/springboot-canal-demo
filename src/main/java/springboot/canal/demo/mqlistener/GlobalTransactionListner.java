package springboot.canal.demo.mqlistener;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 全局事物监听器
 *
 * @description:
 * @author: dada
 * @date: 2020/11/4 20:27
 */
@RocketMQTransactionListener
@Component
public class GlobalTransactionListner implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        System.out.println(msg);

        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        System.out.println("事物通知");
        return RocketMQLocalTransactionState.COMMIT;
    }
}
