package com.example.hungrylama.service;

import com.example.hungrylama.DTO.responseDTOs.OrderResponse;
import com.example.hungrylama.Enum.Coupon;
import com.example.hungrylama.converter.OrderConverter;
import com.example.hungrylama.exception.CustomerNotFoundException;
import com.example.hungrylama.exception.EmptyBasketException;
import com.example.hungrylama.model.*;
import com.example.hungrylama.repository.*;
import com.example.hungrylama.utility.MailComposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    final DeliveryPartnerRepository deliveryPartnerRepository;
    final BillRepository billRepository;
    final BasketRepository basketRepository;
    final JavaMailSender javaMailSender;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,
                        DeliveryPartnerRepository deliveryPartnerRepository, BillRepository billRepository,
                        BasketRepository basketRepository, JavaMailSender javaMailSender) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.billRepository = billRepository;
        this.basketRepository = basketRepository;
        this.javaMailSender = javaMailSender;
    }

    public OrderResponse placeOrder(String email) {
        // validate customer ============================================================================
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException("Invalid Customer");
        }
        // if customer is valid, get customer, its basket and restaurant ================================
        Customer customer = customerOptional.get();
        Basket basket = customer.getBasket();
        // check if basket is empty or not
        if(basket.getBasketItemList().isEmpty()){
            throw new EmptyBasketException("Can't make this order. You basket is empty.");
        }
        Restaurant restaurant = basket.getBasketItemList().get(0).getFoodItem().getRestaurant();
        // after making all the checks, add the order to customer's totalOrder count =====================
        customer.setNumberOfOrders(customer.getNumberOfOrders() + 1);
        // get a delivery partner to assign the order (get is randomly) ==================================
        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findRandom();
        // generate a new bill for the order =============================================================
        Bill Bill = new Bill();
        Bill bill = billRepository.save(Bill);
        // create a new order and save to DB for creating DB-generated data ==============================
        OrderEntity Order = new OrderEntity();
        Order.setOrderId(String.valueOf(UUID.randomUUID()));
        OrderEntity order = orderRepository.save(Order);
        // set relation with other entities ==============================================================
        customer.getOrders().add(order);
        order.setCustomer(customer);

        basket.setOrder(order);
        order.setBasket(basket);

        restaurant.getOrders().add(order);
        order.setRestaurant(restaurant);

        deliveryPartner.getOrders().add(order);
        order.setDeliveryPartner(deliveryPartner);

        bill.setOrder(order);
        order.setBill(bill);
        // remove all the basket items from basket and add them to the order. Also set basket value to zero =========================
        for(BasketItem item : basket.getBasketItemList()){
            item.setOrder(order);
            order.getDishes().add(item);
            item.setBasket(null);
        }
        // clear basket and set basket value to zero
        double basketValue = basket.getBasketValue();
        basket.setBasketValue(0);
        basket.getBasketItemList().clear();
        customerRepository.save(customer);
        // set bill values =====================================================================================================
        bill.setOrderValue(basketValue);
        bill.setGst((double)(0.05 * basketValue));
        // see if any coupon can be applied to the order or not
        Coupon coupon = null;
        if(customer.getNumberOfOrders() == 1){
            coupon = Coupon.FIRST_ORDER;
        } else if(bill.getOrderValue() > 499 && bill.getOrderValue() <= 799){
            coupon = Coupon.ORDER_ABOVE_499;
        } else if(bill.getOrderValue() > 799){
            coupon = Coupon.ORDER_ABOVE_799;
        } else {
            coupon = Coupon.NO_COUPON_APPLIED;
        }
        bill.setCoupon(coupon);
        // calculate discount
        double discount = 0;
        double gstPlusOrderVal = (0.05 * basketValue) + basketValue;
        if(bill.getCoupon().equals(Coupon.FIRST_ORDER)){
            discount = ((double) 30 /100) * gstPlusOrderVal;
        } else if(bill.getCoupon().equals(Coupon.ORDER_ABOVE_499)){
            discount = ((double) 10 /100) * gstPlusOrderVal;
        } else if(bill.getCoupon().equals(Coupon.ORDER_ABOVE_799)){
            discount = ((double) 15 /100) * gstPlusOrderVal;
        }
        bill.setDiscount(discount);
        bill.setBillAmount(gstPlusOrderVal - discount);
        // send mail
        // SimpleMailMessage message = MailComposer.composeOrderPlacementMail(order);
        // javaMailSender.send(message);
        // prepare Order Response
        return OrderConverter.fromOrderDetailsToOrderResponse(order, bill, restaurant, customer);
    }
}
