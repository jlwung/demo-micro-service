package com.example.servicegateway.feignclient;

import org.example.common.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-user")
public interface UserServiceClient {
    @GetMapping("/api/v1/users/{id}")
    User getUser(@PathVariable("id") String id);
}
