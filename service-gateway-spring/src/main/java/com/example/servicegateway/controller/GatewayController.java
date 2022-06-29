package com.example.servicegateway.controller;

import brave.Tracer;
import brave.baggage.BaggageField;
import brave.propagation.ExtraFieldPropagation;
import com.example.servicegateway.feignclient.RoleServiceClient;
import com.example.servicegateway.feignclient.UserServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.example.common.model.Gateway;
import org.example.common.model.Role;
import org.example.common.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class GatewayController {

    private final RoleServiceClient roleServiceClient;
    private final UserServiceClient userServiceClient;
    private final Tracer tracer;

    GatewayController(RoleServiceClient roleServiceClient,
                      UserServiceClient userServiceClient,
                      Tracer tracer){
        this.roleServiceClient = roleServiceClient;
        this.userServiceClient = userServiceClient;
        this.tracer = tracer;
    }

    @GetMapping("/gateways/{id}")
    public Gateway getGateway(@PathVariable String id){
        log.info("Id is {}", id);

        BaggageField baggageField = BaggageField.create("X-TEST");
        baggageField.updateValue("ever bridge");

        Role role = roleServiceClient.getRole(id);
        User user = userServiceClient.getUser(id);
        String traceId = tracer.currentSpan().context().traceIdString();

        Gateway gateway = new Gateway();
        gateway.setTraceId(traceId);
        gateway.setRoleName(role.getName());
        gateway.setUserName(user.getName());

        return gateway;
    }
}
