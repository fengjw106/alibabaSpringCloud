package com.example.cloud.payment.service.impl;

import com.example.cloud.common.CommonResult;
import com.example.cloud.payment.dao.PaymentDAO;
import com.example.cloud.payment.entities.PaymentDO;
import com.example.cloud.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDAO paymentDAO;

    @Override
    public CommonResult<Integer> creatPayment(PaymentDO paymentDO) {
        int creat = paymentDAO.creat(paymentDO);
        log.info("*****插入操作返回结果:{}",creat);
        if (creat > 0) {
            return new CommonResult<>(200, "插入数据库成功", creat);
        } else {
            return new CommonResult<>(444, "插入数据库失败", null);
        }
    }

    @Override
    public CommonResult<PaymentDO> selectById(Long id) {
        PaymentDO paymentDO = paymentDAO.getById(id);
        log.info("*****查询结果:{}", paymentDO);
        if (paymentDO != null) {
            return new CommonResult<>(200, "查询成功", paymentDO);
        } else {
            return new CommonResult<>(444, "没有对应记录,查询ID: " + id, null);
        }
    }
}
