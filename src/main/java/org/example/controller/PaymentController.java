package org.example.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.DTO.CreateSessionDTO;
import org.example.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam(name = "orderId") String orderIdHashed){
        return paymentService.paymentSuccess(orderIdHashed);
    }

    @PostMapping("/create-checkout-session")
    public String createCheckoutSession(HttpServletRequest request,
                                         @RequestBody CreateSessionDTO createSessionDTO) throws StripeException{
        Session session = paymentService.createCheckoutSession(request, createSessionDTO.getOrderId());
        return session.getUrl();
    }
}
