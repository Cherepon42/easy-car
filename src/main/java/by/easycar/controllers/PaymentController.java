package by.easycar.controllers;

import by.easycar.model.Payment;
import by.easycar.model.user.UserPrincipal;
import by.easycar.service.PaymentService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pays")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    private ResponseEntity<String> deposit(@RequestBody String jwt) {
        paymentService.verifyAndMakePay(jwt);
        return new ResponseEntity<>("Payment was created", HttpStatus.OK);
    }

    @GetMapping("/payments-of-user")
    private ResponseEntity<List<Payment>> getPaymentsOfUser(@AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal userPrincipal) {
        List<Payment> payments = paymentService.getPaymentsOfUser(userPrincipal.getId());
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PostMapping("/get-token-for-demonstration")
    private ResponseEntity<String> getToken(@RequestBody Map<String, String> paymentRequest) {
        String token = paymentService.getToken(paymentRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}