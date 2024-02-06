package org.example.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    Session createCheckoutSession(HttpServletRequest request,
                                  Long orderId) throws StripeException;

    String paymentSuccess(String orderIdHashed);
}
