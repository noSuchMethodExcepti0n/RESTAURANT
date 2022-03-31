package js.RESTaurant.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Payment;
import js.RESTaurant.server.service.IPaymentService;


@RestController
@RequestMapping("/payments") 
public class PaymentController {

	@Autowired
	IPaymentService paymentService;

	
	@GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> list = paymentService.getAllPayments();
        return new ResponseEntity<List<Payment>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Payment entity = paymentService.getPaymentById(id);
        return new ResponseEntity<Payment>(entity, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/order/{id}")
    public ResponseEntity<Payment> getPaymentByOrderID(@PathVariable("id") Long id) throws RecordNotFoundException {
        Payment entity = paymentService.getPaymentByOrderId(id);
        return new ResponseEntity<Payment>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping
    public ResponseEntity<Payment> createOrUpdatePayment(@RequestBody Payment payment) throws RecordNotFoundException {
        Payment updated = paymentService.createOrUpdatePayment(payment);
        return new ResponseEntity<Payment>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public HttpStatus deletePaymentById(@PathVariable("id") Long id) throws RecordNotFoundException {
        paymentService.deletePaymentById(id);
        return HttpStatus.FORBIDDEN;
    }
}
