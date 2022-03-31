package js.RESTaurant.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.OrderItem;
import js.RESTaurant.server.model.User;
import js.RESTaurant.server.repository.OrderItemRepository;
import js.RESTaurant.server.repository.UserRepository;
import js.RESTaurant.server.service.IOrderItemService;

@Service
public class OrderItemService implements IOrderItemService {

	@Autowired
	OrderItemRepository orderItemRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public List<OrderItem> getAllOrderItems()
    {
        List<OrderItem> orderItemList = (List<OrderItem>) orderItemRepository.findAll();
         
        if(orderItemList.size() > 0) {
            return orderItemList;
        } else {
            return new ArrayList<OrderItem>();
        }
    }
     
    public OrderItem getOrderItemById(Long id) throws RecordNotFoundException 
    {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
         
        if(orderItem.isPresent()) {
            return orderItem.get();
        } else {
            throw new RecordNotFoundException("No orderItem record exist for given id");
        }
    }
     
    public OrderItem createOrUpdateOrderItem(OrderItem entity) throws RecordNotFoundException 
    {
        Optional<OrderItem> orderItem = orderItemRepository.findById(entity.getOrderItemID());
         
        if(orderItem.isPresent()) 
        {
            OrderItem newEntity = orderItem.get();
            newEntity.setItem(entity.getItem());
            newEntity.setOrder(entity.getOrder());
            newEntity.setQuantity(entity.getQuantity());
 
            newEntity = orderItemRepository.save(newEntity);
             
            return newEntity;
        } else {
        	entity = orderItemRepository.save(entity);
            return entity;
        }
    } 
     
    public void deleteOrderItemById(Long id) throws RecordNotFoundException 
    {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
         
        if(orderItem.isPresent()) 
        {
            orderItemRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No orderItem record exist for given id");
        }
    }

	@Override
	public void mergeLocalCart(List<OrderItem> cartFromCookies, User user) {
		List<OrderItem> finalCart = user.getOrderItemList().stream().filter(orderItem -> orderItem.getOrder() == null).collect(Collectors.toList());
		for (OrderItem orderItem : cartFromCookies) {
			Optional<OrderItem> currentOrderItem = finalCart.stream()
					.filter(e -> e.getItem().getItemID().equals(orderItem.getItem().getItemID()))
					.findFirst();
			
			if(currentOrderItem.isPresent()) {
				Integer quantity = Integer.parseInt(currentOrderItem.get().getQuantity()) + Integer.parseInt(orderItem.getQuantity());
				currentOrderItem.get().setQuantity(quantity.toString());
			} else {
				orderItem.setUser(user);
				finalCart.add(orderItem);
				orderItemRepository.save(orderItem);
			}
		}
		user.setOrderItemList(finalCart);
		userRepository.save(user);
	}
}
