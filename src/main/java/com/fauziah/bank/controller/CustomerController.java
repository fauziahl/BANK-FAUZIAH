package com.fauziah.bank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fauziah.bank.dto.CustomerDTO;
import com.fauziah.bank.handler.ResponseHandler;
import com.fauziah.bank.service.CustomerService;

import jakarta.validation.Valid;

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

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    //CREATE
    @PostMapping("")
    public ResponseHandler createCustomer(@RequestBody @Valid CustomerDTO request, BindingResult bindingResult){
        ResponseHandler response = new ResponseHandler();

        System.out.println("fullname = '" + request.getFullname() + "'");
        System.out.println("bindingResult.hasErrors() = " + bindingResult.hasErrors());

        if(bindingResult.hasErrors()){
            response.setStatus("fail");
            response.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return response;
        }

        response = customerService.createCustomer(request);
        return response;
    }

    //FIND ALL
    @GetMapping("")
    public ResponseHandler getAllCustomer(){
        ResponseHandler response = new ResponseHandler();

        response = customerService.getAllCustomer();
        return response;
    }

    //FIND BY NUMBER ID
    @GetMapping("/{id}")
    public ResponseHandler getCustomerById(@PathVariable("id") String request){
        ResponseHandler response = new ResponseHandler();

        response = customerService.getCustomerByNumberId(request);
        return response;
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseHandler createCustomer(@PathVariable String id, @RequestBody CustomerDTO request, BindingResult bindingResult){
        ResponseHandler response = new ResponseHandler();

        if(bindingResult.hasErrors()){
            response.setStatus("fail");
            response.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return response;
        }

        response = customerService.updateCustomer(id, request);
        return response;
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseHandler deleteCustomerById(@PathVariable("id") String request){
        ResponseHandler response = new ResponseHandler();

        response = customerService.deleteCustomer(request);
        return response;
    }
}
