package com.fauziah.bank.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fauziah.bank.dto.CustomerDTO;
import com.fauziah.bank.entity.Customer;
import com.fauziah.bank.handler.ResponseHandler;
import com.fauziah.bank.repo.CustomerRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepo customerRepo;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomerService.class);

    
    public CustomerService(CustomerRepo customerRepo){
        this.customerRepo = customerRepo;
    }

    //CREATE
    public ResponseHandler createCustomer(CustomerDTO request){
        ResponseHandler response = new ResponseHandler();
        Customer customer = new Customer();
        
        try {
            Optional<Customer> isCustomer = customerRepo.findByNumberId(request.getNumberId());
            if(isCustomer.isPresent()){
                response.setStatus("Failed");
                response.setMessage("Data is available");
            }else{
                customer.setNumberId(request.getNumberId());
                customer.setFullname(request.getFullname());
                customer.setAddress(request.getAddress());
                customer.setPlaceOfBirth(request.getPlaceOfBirth());
                customer.setDob(request.getDob());
                customer.setPhoneNumber(request.getPhoneNumber());
                customer.setCreatedDate(LocalDate.now());
    
                customerRepo.save(customer);
    
                response.setStatus("Success");
                response.setMessage("Success create data");
                logger.info("Create customer: {}", customer);
            }
            
        } catch (Exception e) {
            response.setStatus("Failed");
            response.setError(e.getClass().getSimpleName());
            response.setMessage(e.getMessage());
        }
        
        logger.info("Status: {}, Message: {}", response.getStatus(), response.getMessage());
        return response;
    }

    //FIND ALL
    public ResponseHandler getAllCustomer(){
        ResponseHandler response = new ResponseHandler();
        try {
            List<Customer> customer = customerRepo.findAll();
            
            if(customer.isEmpty()){
                response.setStatus("Success");
                response.setMessage("Data is empty");
            }else{
                response.setStatus("Success");
                response.setMessage("Success fetch data");
                response.setData(customer);
            }
        } catch (Exception e) {
            response.setStatus("Failed");
            response.setError(e.getClass().getSimpleName());
            response.setMessage(e.getMessage());
        }

        logger.info("Status: {}, Message: {}", response.getStatus(), response.getMessage());
        return response;
    }

    //FIND BY NUMBER ID
    public ResponseHandler getCustomerByNumberId(String request){
        ResponseHandler response = new ResponseHandler();
        
        try {
            Optional<Customer> customer = customerRepo.findByNumberId(request);
            
            if(!customer.isPresent()){
                response.setStatus("Success");
                response.setMessage("Data not found");
            }else{
                response.setStatus("Success");
                response.setMessage("Success fetch data");
                response.setData(customer);
            }
        } catch (Exception e) {
            response.setStatus("Failed");
            response.setError(e.getClass().getSimpleName());
            response.setMessage(e.getMessage());
        }

        logger.info("Status: {}, Message: {}", response.getStatus(), response.getMessage());
        return response;
    }

    //UPDATE
    public ResponseHandler updateCustomer(String id, CustomerDTO request){
        ResponseHandler response = new ResponseHandler();
        
        try {
            Optional<Customer> isCustomer = customerRepo.findByNumberId(id);
            if(!isCustomer.isPresent()){
                response.setStatus("Failed");
                response.setMessage("Data not found");
            }else{
                Customer customer = isCustomer.get();
                if(request.getFullname() != null){
                    customer.setFullname(request.getFullname());
                }
                if(request.getAddress() != null){
                    customer.setAddress(request.getAddress());
                }
                if(request.getPlaceOfBirth() != null){
                    customer.setPlaceOfBirth(request.getPlaceOfBirth());
                }
                if(request.getDob() != null){
                    customer.setDob(request.getDob());
                }
                if(request.getPhoneNumber() != null){
                    customer.setPhoneNumber(request.getPhoneNumber());
                }

                customer.setModifiedBy(2);
                customer.setModifiedDate(LocalDate.now());

                customerRepo.save(customer);
    
                response.setStatus("Success");
                response.setMessage("Success create data");
                response.setData(customer);
                logger.info("Update customer: {}", customer);
            }
        } catch (Exception e) {
            response.setStatus("Failed");
            response.setError(e.getClass().getSimpleName());
            response.setMessage(e.getMessage());
        }

        logger.info("Status: {}, Message: {}", response.getStatus(), response.getMessage());
        return response;
    }

    //DELETE
    public ResponseHandler deleteCustomer(String request){
        ResponseHandler response = new ResponseHandler();

        try {
            Optional<Customer> customer = customerRepo.findByNumberId(request);
            
            if(!customer.isPresent()){
                response.setStatus("Success");
                response.setMessage("Data not found");
            }else{
                customerRepo.deleteByNumberId(request);
                response.setStatus("Success");
                response.setMessage("Success delete data with number id: " + request);
            }
            
        } catch (Exception e) {
            response.setStatus("Failed");
            response.setError(e.getClass().getSimpleName());
            response.setMessage(e.getMessage());
        }

        logger.info("Status: {}, Message: {}", response.getStatus(), response.getMessage());
        return response;
    }
}
