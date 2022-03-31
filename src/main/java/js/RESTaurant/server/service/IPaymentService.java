package js.RESTaurant.server.service;

import java.util.List;

import js.RESTaurant.server.enums.PaymentType;
import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.OrderItem;
import js.RESTaurant.server.model.Payment;




public interface IPaymentService {
	List<Payment> getAllPayments();
	Payment getPaymentById(Long id) throws RecordNotFoundException;
	Payment createPayment(Payment entity) throws RecordNotFoundException;
	Payment createOrUpdatePayment(Payment entity) throws RecordNotFoundException;
	void deletePaymentById(Long id) throws RecordNotFoundException;
	
	Payment getPaymentByOrderId(Long orderID) throws RecordNotFoundException;
	Payment createOrderPayment(List<OrderItem> orderItems, PaymentType paymentType);
}
