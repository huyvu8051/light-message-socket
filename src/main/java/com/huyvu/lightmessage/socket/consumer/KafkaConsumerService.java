package com.huyvu.lightmessage.socket.consumer;

import com.huyvu.lightmessage.entity.MessageEntity;
import io.socket.socketio.server.SocketIoNamespace;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import io.socket.socketio.server.SocketIoServer;

import static com.huyvu.lightmessage.socket.ChatBroadcastProvider.CHAT_NAMESPACE;

@Slf4j
@Service
public class KafkaConsumerService {
    static final String TOPIC = "socket-message";
    private final SocketIoServer sio;

    public KafkaConsumerService(SocketIoServer sio) {
        this.sio = sio;
    }

    @KafkaListener(topics = TOPIC, groupId = "my-group")
    public void listen(MessageEntity message) {
        log.info("Received message: {}",message);

        var namespace = sio.namespace(CHAT_NAMESPACE);
    }
}
