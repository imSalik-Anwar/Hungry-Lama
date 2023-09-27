package com.example.hungrylama.controller;

import com.example.hungrylama.DTO.responseDTOs.BasketResponse;
import com.example.hungrylama.DTO.responseDTOs.CustomerResponse;
import com.example.hungrylama.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
public class BasketController {

    final BasketService basketService;
    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/add-dish-to-basket/email/{email}/password/{password}")
    public ResponseEntity addDishToBasket(@PathVariable("email") String email, @PathVariable("password") String password,
                                          @RequestParam("dish") String dishName, @RequestParam("restaurant") String restaurant,
                                          @RequestParam("quantity") int quantity){
        try{
            CustomerResponse response = basketService.addDishToBasket(email, password, dishName, restaurant, quantity);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e){
            return  new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/see-basket/email/{email}")
    public ResponseEntity seeBasket(@PathVariable("email") String email){
        try{
            BasketResponse response = basketService.seeBasket(email);
            return new ResponseEntity(response, HttpStatus.FOUND);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/clear-basket/email/{email}")
    public ResponseEntity<String> clearBasket(@PathVariable("email") String email){
        try{
            basketService.clearBasket(email);
            return new ResponseEntity<>("All dishes have been removed from your basket.", HttpStatus.OK);
        } catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
