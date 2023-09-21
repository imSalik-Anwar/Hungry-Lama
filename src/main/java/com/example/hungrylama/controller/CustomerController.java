package com.example.hungrylama.controller;

import com.example.hungrylama.DTO.requestDTOs.CustomerRequest;
import com.example.hungrylama.DTO.responseDTOs.CustomerResponse;
import com.example.hungrylama.exception.CustomerNotFoundException;
import com.example.hungrylama.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/add-customer")
    public ResponseEntity addNewCustomer(@RequestBody CustomerRequest customerRequest){
        try{
            CustomerResponse response = customerService.addNewCustomer(customerRequest);
            return new ResponseEntity(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity("Input format unmatched!", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-customer/email/{email}/password/{password}")
    public ResponseEntity getCustomer(@PathVariable("email") String email, @PathVariable("password") String password){
        try{
            CustomerResponse response = customerService.getCustomer(email, password);
            return new ResponseEntity(response, HttpStatus.FOUND);
        } catch (CustomerNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
