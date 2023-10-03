package com.example.hungrylama.utility;

import com.example.hungrylama.model.Customer;
import com.example.hungrylama.model.DeliveryPartner;
import com.example.hungrylama.model.OrderEntity;
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
                +"We strongly recommend you to keep your password safe. Otherwise someone else will eat from your basket on your pocket :):";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hungryllama.service@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Hungry Llama Heartily Welcomes You!");
        message.setText(text);

        return message;
    }

    public static SimpleMailMessage composeDeliverPartnerOnboardingMail(DeliveryPartner deliveryPartner){
        String text = "Dear "+deliveryPartner.getName()+",\n"
                +"\n"
                +"Hungry Llama welcomes you to our elite fleet of food deliver partners. Following are your details with Hungry Llama: \n"
                +"\n"
                +"Your Good Name: "+deliveryPartner.getName()+"\n"
                +"Contact: "+deliveryPartner.getContactNumber()+"\n"
                +"Email: "+deliveryPartner.getEmail()+"\n"
                +"Gender: "+deliveryPartner.getGender()+"\n"
                +"\n"
                +"We follow a strict no-discrimination policy against race, caste, religion and gender, and we assure you of having "
                +"a supportive and fair environment to work with Hungry Llama. Before you hop onto delivering your first order, we recommend you to go "
                +"through our policies and guidance on our website to help you adopt best practices as a delivery partner.\n"
                +"\n"
                +"\n"
                +"Keep delivering happiness with Hungry Llama :)";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hungryllama.service@gmail.com");
        message.setTo(deliveryPartner.getEmail());
        message.setSubject("Welcome to The Hungry Llama Family!");
        message.setText(text);

        return message;
    }

    public static SimpleMailMessage composeOrderPlacementMail(OrderEntity order) {
        String text = "Dear "+order.getCustomer().getName()+",\n"
                +"\n"
                +"You order was placed successfully and one of our delivery partners is on their way to get your order delivered ASAP. "
                +"Following are your order details: \n"
                +"\n"
                +"Your Good Name: "+order.getCustomer().getName()+"\n"
                +"Address: "+order.getCustomer().getZipCode()+", "+order.getCustomer().getCity()+"\n"
                +"Order Id: "+order.getOrderId()+"\n"
                +"Restaurant: "+order.getRestaurant().getName()+", "+order.getRestaurant().getLocation()+"\n"
                +"Delivery Partner: "+order.getDeliveryPartner().getName()+"\n"
                +"Delivery Partner Contact: "+order.getDeliveryPartner().getContactNumber()+"\n"
                +"Amount Payable: "+order.getBill().getBillAmount()+"\n"
                +"\n"
                +"For more details on your order, go through the invoice attached.\n"
                +"\n"
                +"\n"
                +"Happy eating :)";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hungryllama.service@gmail.com");
        message.setTo(order.getCustomer().getEmail());
        message.setSubject("Your order is on its way!");
        message.setText(text);

        return message;
    }
}
