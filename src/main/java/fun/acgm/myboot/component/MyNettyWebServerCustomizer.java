package fun.acgm.myboot.component;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;
import reactor.netty.http.server.HttpServer;

import javax.annotation.Resource;

@Component
public class MyNettyWebServerCustomizer implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {
    @Resource
    private EventLoopNettyCustomizer eventLoopNettyCustomizer;

    @Override
    public void customize(NettyReactiveWebServerFactory factory) {
        factory.addServerCustomizers(eventLoopNettyCustomizer);
    }
}

@Component
class EventLoopNettyCustomizer implements NettyServerCustomizer {
    @Value("${event.loop.threads}")
    private int threads;

    @Override
    public HttpServer apply(HttpServer httpServer) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(threads);
        eventLoopGroup.register(new NioServerSocketChannel());
        return httpServer.runOn(eventLoopGroup);
    }
}
