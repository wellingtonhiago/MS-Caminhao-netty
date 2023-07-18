package br.com.inteligate.netty;

import br.com.inteligate.netty.server.CarServer;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class NettyApplication {
	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
	}

	@Bean(initMethod = "start", destroyMethod = "shutdown")
	public CarServer carServer() {
		return new CarServer();
	}

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient();
	}
}
