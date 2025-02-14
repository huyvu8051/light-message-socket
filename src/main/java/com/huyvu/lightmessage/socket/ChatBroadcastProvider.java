package com.huyvu.lightmessage.socket;


import com.huyvu.lightmessage.entity.MessageKafkaDTO;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
public class ChatBroadcastProvider {
    public static final String CHAT_NAMESPACE = "chat";
    private static final String AUTHORIZATION_COOKIE_HEADER = "Authorization";
    private final SocketIoServer server;
    public ChatBroadcastProvider(SocketIoServer sio) {
        this.server = sio;
        var namespace = sio.namespace(CHAT_NAMESPACE);
        namespace.on("connection", args -> {
            SocketIoSocket socket = (SocketIoSocket) args[0];

            socket.send("message", "hi !!!");


            var userId = getUserId(socket);
            if (userId == null) return;

            log.info("Client connected {} {}", userId, socket.getId());

            socket.joinRoom(userId);

            socket.on("disconnect", args1 -> {
                log.info("Client disconnected {} {}", userId, socket.getId());
            });
        });
    }

    private String getUserId(SocketIoSocket socket) {
        var initialHeaders = socket.getInitialHeaders();
        var cookie = initialHeaders.get("cookie");
        if (cookie == null) {
            log.error("cookie is null");
            socket.disconnect(true);
            return null;
        }
        var cookieFirst = cookie.getFirst();
        var parse = this.parseCookies(cookieFirst);

        var cookieOptional = parse.stream()
                .filter(c -> AUTHORIZATION_COOKIE_HEADER.equals(c.getName()))
                .findFirst();

        if (cookieOptional.isEmpty()) {
            log.error("cookie not found");
            socket.disconnect(true);
            return null;
        }

        var httpCookie = cookieOptional.get();
        var userId = httpCookie.getValue();
        if (userId.isBlank()) {
            log.error("cookie value is blank");
            socket.disconnect(true);
            return null;
        }
        return userId;
    }

    private List<HttpCookie> parseCookies(String header) {
        List<HttpCookie> cookies = new ArrayList<>();
        String[] cookiePairs = header.split("; "); // Tách từng cookie

        for (String pair : cookiePairs) {
            try {
                cookies.addAll(HttpCookie.parse(pair)); // Parse từng cookie riêng lẻ
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid cookie format: " + pair);
            }
        }
        return cookies;
    }
    public void send(String userId, MessageKafkaDTO message) {

        var namespace = server.namespace(CHAT_NAMESPACE);
        namespace.broadcast(userId, "message", JsonUtils.toJsonObj(message));


    }
}