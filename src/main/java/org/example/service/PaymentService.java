package org.example.service;

import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface PaymentService {
    void createCheckoutSession(HttpServletResponse response,
                               HttpServletRequest request,
                               Long orderId) throws StripeException, IOException;

    String paymentSuccess(String orderIdHashed);
}
