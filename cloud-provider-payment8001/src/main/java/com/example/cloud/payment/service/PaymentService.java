package com.example.cloud.payment.service;

import com.example.cloud.common.CommonResult;
import com.example.cloud.payment.entities.PaymentDO;

public interface PaymentService {
    CommonResult<Integer> creatPayment(PaymentDO paymentDO);
    CommonResult<PaymentDO> selectById(Long id);
}
