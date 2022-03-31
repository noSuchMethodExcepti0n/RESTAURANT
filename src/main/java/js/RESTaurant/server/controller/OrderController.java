package js.RESTaurant.server.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import js.RESTaurant.server.enums.OrderStatus;
import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Order;
import js.RESTaurant.server.model.OrderItem;
import js.RESTaurant.server.repository.OrderRepository;
import js.RESTaurant.server.request.OrderStatusForm;
import js.RESTaurant.server.service.IOrderService;
import js.RESTaurant.server.service.IPaymentService;


@RestController
@RequestMapping("/orders") 
@CrossOrigin(origins="http://localhost:4200")
public class OrderController {

	@Autowired
	IOrderService orderService;
	
	@Autowired
	IPaymentService paymentService;
	
	@Autowired
	OrderRepository orderRepository;

	
//	@GetMapping
//    public ResponseEntity<List<Order>> getAllOrders() {
//        List<Order> list = orderService.getAllOrders();
//        return new ResponseEntity<List<Order>>(list, new HttpHeaders(), HttpStatus.OK);
//    }
//	
	@GetMapping
    public Page<Order> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "8") Integer size) {
        
		PageRequest request = PageRequest.of(page - 1, size);
		Page<Order> orders = orderService.getAllOrders(request);
		return orders;
    }
	
	@GetMapping("/user")
    public Page<Order> userOrders(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "7") Integer size) {
        
		PageRequest request = PageRequest.of(page - 1, size);
		Page<Order> orders = orderService.getUserOrders(request);
		return orders;
    }
 
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Order order = orderService.getOrderById(id);
    	return order;
    }
 
    @PostMapping
    public ResponseEntity<Order> createOrUpdateOrder(@RequestBody Order order) throws RecordNotFoundException {
        Order updated = orderService.updateOrder(order);
        return new ResponseEntity<Order>(updated, new HttpHeaders(), HttpStatus.OK);
    }
    
    @PostMapping("/checkout")
    public ResponseEntity<Order> makeOrder(@RequestBody Order order) throws RecordNotFoundException {
    	Order entity = null;
    	try {
        	Order createdOrder = orderService.createOrder(order);
        	entity = orderService.getOrderById(createdOrder.getOrderID());
        	return new ResponseEntity<Order>(entity, new HttpHeaders(), HttpStatus.OK);
        	
        } catch (Exception e) {
            
        }
        return new ResponseEntity<Order>(entity, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @PostMapping("/status")
    public boolean changeOrderStatus(@RequestBody OrderStatusForm orderStatusForm) throws RecordNotFoundException {
    	try {
    		Order order = orderService.getOrderById(orderStatusForm.getOrderID());
        	order.setStatus(orderStatusForm.getOrderStatus());
        	orderService.saveOrder(order);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
 
    @DeleteMapping("/{id}")
    public HttpStatus deleteOrderById(@PathVariable("id") Long id) throws RecordNotFoundException {
        orderService.deleteOrderById(id);
        return HttpStatus.OK;
    }
}
