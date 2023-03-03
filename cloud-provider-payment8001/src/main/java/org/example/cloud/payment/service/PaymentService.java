package org.example.cloud.payment.service;

import org.example.cloud.entities.CommonResult;
import org.example.cloud.entities.PaymentDO;

public interface PaymentService {
    CommonResult<Integer> creatPayment(PaymentDO paymentDO);

    CommonResult<PaymentDO> selectById(Long id);

    String paymentFeignTimeOut();
}
