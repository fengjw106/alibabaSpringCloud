package com.example.cloud.payment.Controller;

import com.example.cloud.common.CommonResult;
import com.example.cloud.payment.entities.PaymentDO;
import com.example.cloud.payment.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/web/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping("/creatPayment")
    public CommonResult<Integer> creatPayment(@RequestBody PaymentDO paymentDO){
        return paymentService.creatPayment(paymentDO);
    }

    @PostMapping("/selectById")
    public CommonResult<PaymentDO> selectById(@RequestBody Long id){
        return paymentService.selectById(id);
    }
}
