package org.example.cloud.service;

import org.example.cloud.entities.CommonResult;
import org.example.cloud.entities.PaymentDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
@Component
public interface PaymentFeignService {

    @PostMapping("/web/payment/selectById")
    //不加RequestParam，生产者获取不到值，可能是无法解析
    CommonResult<PaymentDO> selectById(@RequestParam("id") Long id);

    @GetMapping("/web/payment/paymentFeignTimeOut")
    String paymentFeignTimeOut();
}
