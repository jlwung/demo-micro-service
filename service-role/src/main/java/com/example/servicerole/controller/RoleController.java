package com.example.servicerole.controller;


import brave.Tracer;
import brave.baggage.BaggageField;
import brave.internal.baggage.ExtraBaggageContext;
import lombok.extern.slf4j.Slf4j;
import org.example.common.model.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private final Tracer tracer;

    RoleController(Tracer tracer){
        this.tracer = tracer;
    }

    @GetMapping("/roles/{id}")
    public Role getRole(@PathVariable("id") String id){
        log.info("Role id is {}", id);
        log.info("Baggage for [x-test] is [" + ExtraBaggageContext.getFieldByName(tracer.currentSpan().context(),"X-TEST").getValue() + "]");

        Role role = new Role();
        role.setName("Role is " + id);
        return role;
    }
}
