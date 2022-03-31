package js.RESTaurant.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import js.RESTaurant.server.enums.PaymentStatus;
import js.RESTaurant.server.enums.PaymentType;
import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Payment;
import js.RESTaurant.server.repository.PaymentRepository;
import js.RESTaurant.server.service.IOrderService;
import js.RESTaurant.server.service.IPaymentService;
import js.RESTaurant.server.model.OrderItem;

@Service
public class PaymentService implements IPaymentService {

	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	IOrderService orderService;
	
	@Override
	public List<Payment> getAllPayments()
    {
        List<Payment> paymentList = (List<Payment>) paymentRepository.findAll();
         
        if(paymentList.size() > 0) {
            return paymentList;
        } else {
            return new ArrayList<Payment>();
        }
    }
    
	@Override
    public Payment getPaymentById(Long id) throws RecordNotFoundException 
    {
        Optional<Payment> payment = paymentRepository.findById(id);
         
        if(payment.isPresent()) {
            return payment.get();
        } else {
            throw new RecordNotFoundException("No payment record exist for given id");
        }
    }
	
	@Override
	public Payment createOrderPayment(List<OrderItem> orderItems, PaymentType paymentType) {
		Payment payment = new Payment();
		payment.setPaymentType(paymentType);
		payment.setStatus(PaymentStatus.PENDING);
		payment.setValue(orderService.calculateOrderValue(orderItems));
		
		return createPayment(payment);
	}
    
	@Override
    public Payment createPayment(Payment entity) throws RecordNotFoundException 
    {
        	entity = paymentRepository.save(entity);
        	return entity;
    } 
    
	@Override
    public Payment createOrUpdatePayment(Payment entity) throws RecordNotFoundException 
    {
        Optional<Payment> payment = paymentRepository.findById(entity.getPaymentID());
         
        if(payment.isPresent()) 
        {
            Payment newEntity = payment.get();
            newEntity.setPaymentType(entity.getPaymentType());
            newEntity.setOrder(entity.getOrder());
            newEntity.setPaymentType(entity.getPaymentType());
            newEntity.setStatus(entity.getStatus());
            newEntity.setValue(entity.getValue());
 
            newEntity = paymentRepository.save(newEntity);
             
            return newEntity;
        } else {
        	entity = paymentRepository.save(entity);
            return entity;
        }
    } 
     
	@Override
    public void deletePaymentById(Long id) throws RecordNotFoundException 
    {
        Optional<Payment> payment = paymentRepository.findById(id);
         
        if(payment.isPresent()) 
        {
            paymentRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No payment record exist for given id");
        }
    }

	@Override
	public Payment getPaymentByOrderId(Long orderID) throws RecordNotFoundException {
		 Optional<Payment> payment = paymentRepository.findByOrderOrderID(orderID);
         
	        if(payment.isPresent()) {
	            return payment.get();
	        } else {
	            throw new RecordNotFoundException("No payment record exist for given order id");
	        }
	} 
}
