package com.example.hungrylama.utility;

import com.example.hungrylama.model.Customer;
import org.springframework.mail.SimpleMailMessage;

public class MailComposer {
    public static SimpleMailMessage composeCustomerRegistrationMail(Customer customer) {
        String text = "Dear "+customer.getName()+",\n"
                +"\n"
                +"You have been successfully added to India's most enthusiastic community of foodies. Following are the your customer details: \n"
                +"\n"
                +"Your Good Name: "+customer.getName()+"\n"
                +"Email: "+customer.getEmail()+"\n"
                +"Password: "+customer.getPassword()+"\n"
                +"Address: "+customer.getZipCode()+", "+customer.getCity()+"\n"
                +"\n"
                +"We strongly recommend you to keep your password safe. Otherwise some else will eat from your basket on your pocket :):";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hungryllama.service@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Hungry Llama Heartily Welcomes You!");
        message.setText(text);

        return message;
    }
}
