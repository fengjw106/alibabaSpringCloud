package org.example.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/web/orderConsul")
public class OrderConsulController {
    public static final String INVOKE_URL = "http://consul-provider-payment";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consul")
    public String paymentInfo() {
        String result = restTemplate.getForObject(INVOKE_URL + "/web/payment/consul", String.class);
        System.out.println("消费者调用支付服务(consul)--->result:" + result);
        return result;
    }

}
