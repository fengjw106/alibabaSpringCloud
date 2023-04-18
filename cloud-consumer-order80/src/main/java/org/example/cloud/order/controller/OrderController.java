package org.example.cloud.order.controller;

import org.example.cloud.entities.CommonResult;
import org.example.cloud.entities.PaymentDO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 客户端用浏览器是get请求，但是底层实质发送post调用服务端8001
 */
@RestController
@RequestMapping("/web/order")
public class OrderController {


    //public static final String PAYMENT_URL = "http://localhost:8001";
    //在集群的模式下，不要写具体的一个服务，写eureka注册的服务名
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";


    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/creatPayment")
    public CommonResult<Integer> creatPayment(PaymentDO paymentDO) {
        return restTemplate.postForObject(PAYMENT_URL + "/web/payment/creatPayment", paymentDO, CommonResult.class);
    }

    @GetMapping("/selectById")
    public CommonResult<PaymentDO> selectById(Long id) {
        //提交x-www-form-urlencoded格式的数据
        //1. 设置请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //2. 提交参数
        LinkedMultiValueMap<String, Long> map = new LinkedMultiValueMap<>();
        map.add("id", id);
        //3. 组装请求体
        HttpEntity<MultiValueMap<String, Long>> request = new HttpEntity<>(map, httpHeaders);
        //4. 发送Post请求
        return restTemplate.postForObject(PAYMENT_URL + "/web/payment/selectById", request, CommonResult.class);
    }

    @GetMapping(value = "/discovery")
    public Object discovery() {
        return restTemplate.getForEntity(PAYMENT_URL + "/web/payment/selectById", Object.class);
    }

    /**
     * zipkin+sleuth
     *
     * @return
     */
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin() {
        String result = restTemplate.getForObject(PAYMENT_URL + "/web/payment/zipkin/", String.class);
        return result;
    }


}
