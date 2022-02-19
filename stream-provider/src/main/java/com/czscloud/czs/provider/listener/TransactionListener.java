package com.czscloud.czs.provider.listener;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

import java.util.Date;

@RocketMQTransactionListener(txProducerGroup = "myTxproduceGroup",corePoolSize = 5,maximumPoolSize = 10)
public class TransactionListener implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try{
            String transactionId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
            System.out.println("executeLocalTransaction transactionId:" + transactionId + "data:"+new Date());
            //编写执行操作
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    /**
     * 如果一定时间后，还有消息未确认发出，RocketMQ会主动掉那个发送方，让调用方解决消息是否该发出，该方法决定该消息是否应该提交还是回滚
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {

        try{
            String transactionId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
            System.out.println("checkLocalTransaction transactionId:" + transactionId + "data:"+new Date());
            //编写执行操作
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
