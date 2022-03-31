package js.RESTaurant.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import js.RESTaurant.server.enums.OrderStatus;
import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Order;
import js.RESTaurant.server.model.OrderItem;


public interface IOrderService {
	Page<Order> getAllOrders(Pageable pageable);
	Order getOrderById(Long id) throws RecordNotFoundException;
	Order createOrder(Order entity) throws RecordNotFoundException;
	Order updateOrder(Order entity) throws RecordNotFoundException;
	void deleteOrderById(Long id) throws RecordNotFoundException;
	
	String calculateOrderValue(List<OrderItem> orderItems);
	
	Page<Order> getUserOrders(Pageable pageable);
	
	void saveOrder(Order order);
	
	Long countOrderByOrderStatus(OrderStatus orderStatus);
	List<Order> getOrdersBetweenDate(Date startDate, Date endDate);
	
	double countEarningsBetweenDate(Date startDate, Date endDate);
}
