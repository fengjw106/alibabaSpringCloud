package org.example.cloud.payment.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {
    /**
     * 正常访问一切 OK
     */
    String paymentInfoOk(Integer id);

    /**
     * 超时访问，演示降级
     */
    String paymentInfoTimeOut(Integer id);

    String paymentCircuitBreaker(@PathVariable("id") Integer id);
}
