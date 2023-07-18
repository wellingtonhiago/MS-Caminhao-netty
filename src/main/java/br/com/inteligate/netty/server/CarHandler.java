package br.com.inteligate.netty.server;

import br.com.inteligate.netty.dto.TruckDTO;
import br.com.inteligate.netty.http.CamundaClient;
import feign.FeignException;
import io.netty.channel.ChannelHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Component
public class CarHandler extends SimpleChannelInboundHandler<String> {
    private final CamundaClient camundaClient;

    public CarHandler(CamundaClient camundaClient) {
        this.camundaClient = camundaClient;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        String[] parts = msg.split(";");
        String placa = parts[0];

        TruckDTO request = new TruckDTO();
        request.setId(placa);

        try {
            String response = camundaClient.startProcess(request);
            System.out.println("Resposta do serviço Camunda: " + response);
        } catch (FeignException e) {
            System.err.println("Erro ao chamar o serviço Camunda: " + e);
        }
    }
}




