package com.example.servicegateway.feignclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.example.common.common.ErrorResponse;
import org.example.common.common.MyException;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

import static feign.FeignException.errorStatus;

@Slf4j
@Configuration
public class StashErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        String s = Optional.ofNullable(response.body()).map(Response.Body::toString).orElse(null);
        log.info("response body:" + s);
        if(StringUtils.isNotBlank(s)) {
            try {
                ErrorResponse errorResponse = objectMapper.readValue(s, ErrorResponse.class);
                if (errorResponse.getCode() >= 1000) {
                    return new MyException(errorResponse.getCode(), errorResponse.getMessage());
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return errorStatus(methodKey, response);
            }
        }
        return errorStatus(methodKey, response);
    }
}