package org.example.cloud.payment.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.cloud.entities.CommonResult;
import org.example.cloud.entities.PaymentDO;
import org.example.cloud.payment.dao.PaymentDAO;
import org.example.cloud.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDAO paymentDAO;

    @Value("${server.port}")
    private String serverPort;

    @Override
    public CommonResult<Integer> creatPayment(PaymentDO paymentDO) {
        int creat = paymentDAO.creat(paymentDO);
        log.info("*****插入操作返回结果:{}", creat);
        if (creat > 0) {
            return new CommonResult<>(200, "插入数据库成功,server port:" + serverPort, creat);
        } else {
            return new CommonResult<>(444, "插入数据库失败,server port:", null);
        }
    }

    @Override
    public CommonResult<PaymentDO> selectById(Long id) {
        PaymentDO paymentDO = paymentDAO.getById(id);
        log.info("*****查询结果:{}", paymentDO);
        if (paymentDO != null) {
            return new CommonResult<>(200, "查询成功，server port：" + serverPort, paymentDO);
        } else {
            return new CommonResult<>(444, "没有对应记录,查询ID: " + id + ",server port:" + serverPort, null);
        }
    }
}
