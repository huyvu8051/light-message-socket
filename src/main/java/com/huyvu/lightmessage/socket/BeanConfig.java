package com.huyvu.lightmessage.socket;

import io.socket.engineio.server.EngineIoServer;
import io.socket.engineio.server.EngineIoServerOptions;
import io.socket.socketio.server.SocketIoServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class BeanConfig {

    @Bean
    EngineIoServer engineIoServer() {
        var opt = EngineIoServerOptions.newFromDefault();
        opt.setCorsHandlingDisabled(true);

        var eioServer = new EngineIoServer(opt);
        return eioServer;
    }

    @Bean
    SocketIoServer socketIoServer(EngineIoServer eioServer) {
        var sioServer = new SocketIoServer(eioServer);
        return sioServer;
    }

}