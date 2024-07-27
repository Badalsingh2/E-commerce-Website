package com.luv2code.ecommerce.controller;

import com.luv2code.ecommerce.dto.PaymentInfo;
import com.luv2code.ecommerce.dto.Purchase;
import com.luv2code.ecommerce.dto.PurchaseResponse;
import com.luv2code.ecommerce.service.CheckOutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private Logger logger=Logger.getLogger(getClass().getName());
    private CheckOutService checkOutService;
    @Autowired
    public CheckoutController(CheckOutService checkOutService){
        this.checkOutService=checkOutService;
    }



    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase){
        System.out.println("Received Purchase: "+purchase);
        PurchaseResponse purchaseResponse = checkOutService.placeOrder(purchase);
        System.out.println("Received Purchase: "+purchase);
        return purchaseResponse;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException{

        logger.info("paymentInf.amount: "+paymentInfo.getAmount());
        PaymentIntent paymentIntent= checkOutService.createPaymentIntent(paymentInfo);

        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
}
