package com.huyvu.lightmessage.socket.consumer;

import com.huyvu.lightmessage.entity.MessageEntity;
import com.huyvu.lightmessage.socket.ChatBroadcastProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    static final String TOPIC = "socket-message";
    private final ChatBroadcastProvider chatBroadcastProvider;

    @KafkaListener(topics = TOPIC, groupId = "my-group")
    public void listen(MessageEntity message) {
        log.info("Received message: {}", message);
        for (var memberId : message.memberIds()) {
            chatBroadcastProvider.send(String.valueOf(memberId), message);
        }
    }
}
