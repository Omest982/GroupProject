package org.example.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import jakarta.servlet.http.HttpServletRequest;
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
    public String createCheckoutSession(HttpServletRequest request,
                                         @RequestBody Long orderId) throws StripeException, IOException {
        Session session = paymentService.createCheckoutSession(request, orderId);
        return session.getUrl();
    }
}
