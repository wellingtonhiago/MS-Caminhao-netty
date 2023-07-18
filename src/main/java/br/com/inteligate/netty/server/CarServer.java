package br.com.inteligate.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public final class CarServer {
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @Autowired
    private ApplicationContext applicationContext;

    public void start() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        new Thread(() -> {
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch) {
                                CarHandler carHandler = applicationContext.getBean(CarHandler.class);
                                ch.pipeline().addLast(new ByteToStringDecoder(), new StringToByteEncoder(), carHandler);
                            }
                        });

                ChannelFuture f = b.bind(PORT).sync();

                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}



