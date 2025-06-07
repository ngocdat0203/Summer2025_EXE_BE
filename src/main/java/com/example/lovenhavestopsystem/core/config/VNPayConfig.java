package com.example.lovenhavestopsystem.core.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class VNPayConfig {

    @Value("${VNPAY_TMN_CODE}")
    private String tmnCode;

    @Value("${VNPAY_SECRET_KEY}")
    private String secretKey;

    @Value("${VNPAY_PAY_URL}")
    private String payUrl;

    @Value("${VNPAY_VERSION}")
    private String version;

}