package js.RESTaurant.server.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import js.RESTaurant.server.enums.OrderStatus;
import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Order;
import js.RESTaurant.server.model.OrderItem;
import js.RESTaurant.server.model.OrderType;
import js.RESTaurant.server.model.Payment;
import js.RESTaurant.server.model.Role;
import js.RESTaurant.server.model.User;
import js.RESTaurant.server.repository.OrderRepository;
import js.RESTaurant.server.service.IOrderItemService;
import js.RESTaurant.server.service.IOrderService;
import js.RESTaurant.server.service.IOrderTypeService;
import js.RESTaurant.server.service.IPaymentService;
import js.RESTaurant.server.service.IUserService;
import js.RESTaurant.server.util.Utils;

@Service
public class OrderService implements IOrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IPaymentService paymentService;
	
	@Autowired
	IOrderItemService orderItemService;
	
	@Autowired
	IOrderTypeService orderTypeService;
	
	
	@Override
	public Page<Order> getAllOrders(Pageable pageable) {
		return orderRepository.findAll(pageable);
    }
     
	@Override 
	@Transactional
    public Order getOrderById(Long id) throws RecordNotFoundException {
        Optional<Order> order = orderRepository.findById(id);
         
        if(order.isPresent()) {
            return order.get();
        } else {
            throw new RecordNotFoundException("No order record exist for given id");
        }
    }
    
	@Override
	@Transactional
    public Order createOrder(Order entity) throws RecordNotFoundException {
		entity.setNumber(Long.toString(Utils.getNext()));
    	entity.setStatus(OrderStatus.PENDING_FOR_PAYMENT);
    	
    	OrderType orderType = orderTypeService.getRoleByName(entity.getOrderType().getName());
    	entity.setOrderType(orderType);
    	
    	User user = userService.getCurrentLoggedUser();
    	entity.setUser(user);
    	
    	List<OrderItem> userCart = user.getOrderItemList().stream().
    			filter(orderItem -> orderItem.getOrder() == null).collect(Collectors.toList());
    	for (OrderItem orderItem : userCart) {
			orderItem.setOrder(entity);
		}
    	
    	Payment payment = paymentService.createOrderPayment(userCart, entity.getPayment().getPaymentType());
    	entity.setPayment(payment);
    	
    	orderRepository.save(entity);
    	
    	return entity;
    } 
     
	@Override
	@Transactional
    public Order updateOrder(Order entity) throws RecordNotFoundException {
    	Optional<Order> order = orderRepository.findById(entity.getOrderID());
         
        if(order.isPresent()) {
            Order newEntity = order.get();
            //newEntity.setOrderItemList(entity.getOrderItemList());
            newEntity.setOrderType(entity.getOrderType());
            newEntity.setPayment(entity.getPayment());
            newEntity.setStatus(entity.getStatus());
            newEntity.setUser(entity.getUser());
            
            newEntity = orderRepository.save(newEntity);
             
            return newEntity;
        } 
//        else {
//            return createOrder(entity);
//        }
		return entity;
    } 
     
	@Override
    public void deleteOrderById(Long id) throws RecordNotFoundException {
        Optional<Order> order = orderRepository.findById(id);
         
        if(order.isPresent()) 
        {
            orderRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No order record exist for given id");
        }
    } 
    
	@Override
    public String calculateOrderValue(List<OrderItem> orderItems) {
    	double orderValue = 0;
    	for (OrderItem orderItem : orderItems) {
    		double itemValue = Double.parseDouble(orderItem.getItem().getValue());
    		int quantity = Integer.parseInt(orderItem.getQuantity());
    		orderValue+=itemValue * quantity;
		}
    	
    	return Utils.formatTo2Places(orderValue);
    }

	@Override
	public Page<Order> getUserOrders(Pageable pageable) {
		User user = userService.getCurrentLoggedUser();
		return orderRepository.findByUser(pageable, user);
	}

	@Override
	public void saveOrder(Order order) {
		orderRepository.save(order);
	}

	@Override
	public Long countOrderByOrderStatus(OrderStatus orderStatus) {
		return orderRepository.countByStatus(orderStatus);
	}

	@Override
	public List<Order> getOrdersBetweenDate(Date startDate, Date endDate) {
		return orderRepository.findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(startDate, endDate);
	}

	@Override
	public double countEarningsBetweenDate(Date startDate, Date endDate) {
		List<Order> todayOrders = getOrdersBetweenDate(startDate, endDate);
		List<Order> compleatedTodayOrders = todayOrders.stream().filter(order -> order.getStatus() == OrderStatus.COMPLEATED).collect(Collectors.toList());
    	double totalrevenue = 0;
    	for (Order order : compleatedTodayOrders) {
			totalrevenue+= Double.valueOf(order.getPayment().getValue());
		}
		
		return totalrevenue;
	}
}
