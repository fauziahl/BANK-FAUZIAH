package com.fauziah.bank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fauziah.bank.dto.CustomerDTO;
import com.fauziah.bank.handler.ResponseHandler;
import com.fauziah.bank.service.CustomerService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("api/customer")
public class CustomerController {

    private CustomerService customerService;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    //CREATE
    @PostMapping("")
    public ResponseHandler createCustomer(@RequestBody @Valid CustomerDTO request, BindingResult bindingResult){
        ResponseHandler response = new ResponseHandler();

        logger.info("Customer request: ", request);

        Map<String, String> errors = new HashMap<>();

        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
            );

            response.setStatus("Failed");
            response.setError("Validation error");
            response.setMessage("Error create customer");
            response.setData(errors);
            logger.warn("Validation error: {}", errors);

            return response;
        }

        try {
            response = customerService.createCustomer(request);
        } catch (Exception e) {
            logger.error("Error create customer", e);

            response.setStatus("Error");
            response.setMessage("Internal server error");
        }

        return response;
    }

    //FIND ALL
    @GetMapping("")
    public ResponseHandler getAllCustomer(){
        logger.info("Get all customer request ");
        return customerService.getAllCustomer();
    }

    //FIND BY NUMBER ID
    @GetMapping("/{id}")
    public ResponseHandler getCustomerById(@PathVariable("id") String request){
        logger.info("Get customer request id {}", request);
        return customerService.getCustomerByNumberId(request);
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseHandler createCustomer(@PathVariable String id, @RequestBody CustomerDTO request, BindingResult bindingResult){
        logger.info("Update customer request id {} data{}", id, request);
        return customerService.updateCustomer(id, request);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseHandler deleteCustomerById(@PathVariable("id") String request){
        logger.info("Delete customer request id {}", request);
        return customerService.deleteCustomer(request);
    }
}
