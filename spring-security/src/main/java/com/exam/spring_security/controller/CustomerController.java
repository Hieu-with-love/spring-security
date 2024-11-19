package com.exam.spring_security.controller;

import com.exam.spring_security.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class CustomerController {
    private final List<Customer> customers = List.of(
            Customer.builder()
                    .id("001")
                    .name("Tran Trung Hieu")
                    .build(),
            Customer.builder()
                    .id("002")
                    .name("Trung Hieu")
                    .build()
    );

    @GetMapping
    public ResponseEntity<?> hello(){
        return ResponseEntity.ok("Hello Guest");
    }

    @GetMapping("/customer/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> all(){
        List<Customer> list = this.customers;
        return ResponseEntity.ok(list);
    }

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getCustomersById(@PathVariable String id){
        List<Customer> listCustomers = this.customers.stream()
                        .filter(c -> c.getId().equals(id)).toList();
        return ResponseEntity.ok(listCustomers);
    }

}
