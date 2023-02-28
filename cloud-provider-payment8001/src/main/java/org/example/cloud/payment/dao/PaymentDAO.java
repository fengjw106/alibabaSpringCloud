package org.example.cloud.payment.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.cloud.entities.PaymentDO;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PaymentDAO {

    int creat(@Param("do") PaymentDO paymentDO);

    PaymentDO getById(@Param("id") Long id);
}
