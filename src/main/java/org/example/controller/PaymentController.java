package org.example.controller;

import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam(name = "orderId") String orderIdHashed){
        return paymentService.paymentSuccess(orderIdHashed);
    }

    @PostMapping("/create-checkout-session")
    public void createCheckoutSession(HttpServletResponse response,
                                      HttpServletRequest request,
                                      @RequestBody Long orderId) throws StripeException, IOException {
        paymentService.createCheckoutSession(response, request, orderId);
    }
}
