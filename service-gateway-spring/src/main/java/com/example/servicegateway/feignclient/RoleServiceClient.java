package com.example.servicegateway.feignclient;

import org.example.common.model.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-role")
public interface RoleServiceClient {
    @GetMapping("/api/v1/roles/{id}")
    Role getRole(@PathVariable("id") String id);
}
