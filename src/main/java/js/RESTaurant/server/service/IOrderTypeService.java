package js.RESTaurant.server.service;

import java.util.List;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.OrderType;


public interface IOrderTypeService {
	List<OrderType> getAllOrderTypes();
	OrderType getOrderTypeById(Long id) throws RecordNotFoundException;
	OrderType getRoleByName(String name) throws RecordNotFoundException;
	OrderType createOrUpdateOrderType(OrderType entity) throws RecordNotFoundException;
	void deleteOrderTypeById(Long id) throws RecordNotFoundException;
}
