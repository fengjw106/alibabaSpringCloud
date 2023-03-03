package org.example.cloud.controller;

import org.example.cloud.entities.CommonResult;
import org.example.cloud.entities.PaymentDO;
import org.example.cloud.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @PostMapping("/web/consumer/payment/selectById")
    public CommonResult<PaymentDO> selectById(@RequestParam("id") Long id) {
        return paymentFeignService.selectById(id);
    }

    @GetMapping("/web/consumer/payment/paymentFeignTimeOut")
    String paymentFeignTimeOut(){
        return paymentFeignService.paymentFeignTimeOut();
    }
}
