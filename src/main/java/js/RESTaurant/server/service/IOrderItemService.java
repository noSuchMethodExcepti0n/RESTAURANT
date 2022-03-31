package js.RESTaurant.server.service;

import java.util.List;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.OrderItem;
import js.RESTaurant.server.model.User;


public interface IOrderItemService {
	List<OrderItem> getAllOrderItems();
	OrderItem getOrderItemById(Long id) throws RecordNotFoundException;
	OrderItem createOrUpdateOrderItem(OrderItem entity) throws RecordNotFoundException;
	void deleteOrderItemById(Long id) throws RecordNotFoundException;
	
	void mergeLocalCart(List<OrderItem> orderItems, User user);
}
