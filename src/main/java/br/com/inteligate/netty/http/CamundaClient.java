package br.com.inteligate.netty.http;

import br.com.inteligate.netty.dto.TruckDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "camunda", url = "http://localhost:8082/camunda-process/")
public interface CamundaClient {

    @PostMapping(value = "/startProcess", consumes = "application/json")
    String startProcess(@RequestBody TruckDTO request);
}
