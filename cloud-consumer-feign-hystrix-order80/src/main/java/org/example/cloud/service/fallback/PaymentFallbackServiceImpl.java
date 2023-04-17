package org.example.cloud.service.fallback;

import org.example.cloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackServiceImpl implements PaymentHystrixService {
    @Override
    public String paymentInfoOk(Integer id) {
        return "====PaymentHystrixService fall back paymentInfoOk，o(╥﹏╥)o====";
    }

    @Override
    public String paymentInfoTimeOut(Integer id) {
        return "====PaymentHystrixService fall back paymentInfoTimeOut，o(╥﹏╥)o====";
    }
}
