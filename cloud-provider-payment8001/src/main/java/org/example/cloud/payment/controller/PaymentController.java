package org.example.cloud.payment.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.cloud.entities.CommonResult;
import org.example.cloud.entities.PaymentDO;
import org.example.cloud.payment.service.PaymentService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/web/payment")
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;
    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/creatPayment")
    public CommonResult<Integer> creatPayment(@RequestBody PaymentDO paymentDO) {
        return paymentService.creatPayment(paymentDO);
    }

    @PostMapping("/selectById")
    public CommonResult<PaymentDO> selectById(Long id) {
        return paymentService.selectById(id);
    }

    @GetMapping(value = "/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("service:{}", service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t"
                    + instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("/paymentFeignTimeOut")
    public String paymentFeignTimeOut() {
        return paymentService.paymentFeignTimeOut();
    }
}
