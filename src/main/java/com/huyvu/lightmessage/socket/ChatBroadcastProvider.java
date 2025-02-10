package com.huyvu.lightmessage.socket;


import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class ChatBroadcastProvider {
    private final SocketIoServer sio;
    public static final String CHAT_NAMESPACE = "chat";

    @Bean(CHAT_NAMESPACE)
    SocketIoNamespace chatNamespace() {
        var namespace = sio.namespace(CHAT_NAMESPACE);
        namespace.on("connection", args -> {
            SocketIoSocket socket = (SocketIoSocket) args[0];
//            socket.joinRoom();
            log.info("Client {} has connected.", socket.getId());
        });
        return namespace;
    }
}