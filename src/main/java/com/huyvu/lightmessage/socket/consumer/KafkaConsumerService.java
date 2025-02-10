package com.huyvu.lightmessage.socket.consumer;

import com.huyvu.lightmessage.entity.MessageEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {
    static final String TOPIC = "socket-message";
    @KafkaListener(topics = TOPIC, groupId = "my-group")
    public void listen(MessageEntity message) {
        log.info("Received message: {}",message);
    }
}
