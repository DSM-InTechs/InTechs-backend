package InTechs.InTechs.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class SocketConfig {

    @Value("${server.socket.port}")
    private Integer port;

    private SocketIOServer server;

    @Bean
    public SocketIOServer webSocketServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setPort(port);

        SocketIOServer server = new SocketIOServer(config);
        server.start();
        this.server = server;

        return server;
    }

    @PreDestroy
    public void socketStop() {
        server.stop();
    }

}
