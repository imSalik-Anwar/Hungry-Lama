package com.example.hungrylama.service;

import com.example.hungrylama.DTO.requestDTOs.CustomerRequest;
import com.example.hungrylama.DTO.responseDTOs.CustomerResponse;
import com.example.hungrylama.converter.CustomerConverter;
import com.example.hungrylama.exception.CustomerNotFoundException;
import com.example.hungrylama.model.Basket;
import com.example.hungrylama.model.Customer;
import com.example.hungrylama.repository.CustomerRepository;
import com.example.hungrylama.utility.MailComposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomerService {
    final CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Autowired
    JavaMailSender javaMailSender;
    public CustomerResponse addNewCustomer(CustomerRequest customerRequest) throws Exception{
        // convert DTO to model
        Customer customer = CustomerConverter.fromCustomerRequestToCustomer(customerRequest);
        // Initialize a new basket for a new customer
        Basket basket = Basket.builder()
                .customer(customer)
                .basketValue(0)
                .foodItems(new ArrayList<>())
                .build();
        // set basket to the customer
        customer.setBasket(basket);
        // save customer and send email
        Customer savedCustomer = customerRepository.save(customer);
        // SimpleMailMessage message = MailComposer.composeCustomerRegistrationMail(savedCustomer);
        // javaMailSender.send(message);
        // convert model to DTO
        return CustomerConverter.fromCustomerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse getCustomer(String email, String password) {
        // get the customer
        Optional<Customer> customerOptional = customerRepository.findByEmailAndPassword(email, password);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException("Invalid input");
        }
        Customer customer = customerOptional.get();
        // convert model to DTO and return
        return CustomerConverter.fromCustomerToCustomerResponse(customer);
    }
}
