package js.RESTaurant.server.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.OrderItem;
import js.RESTaurant.server.model.User;
import js.RESTaurant.server.service.IItemService;
import js.RESTaurant.server.service.IOrderItemService;
import js.RESTaurant.server.service.IUserService;




@RestController
@RequestMapping("/orderItems") 
@CrossOrigin(origins="http://localhost:4200")
public class OrderItemController {

	@Autowired
	IOrderItemService orderItemService;
	
	@Autowired
	IItemService itemService;
	
	@Autowired
	IUserService userService;

	
	@GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> list = orderItemService.getAllOrderItems();
        return new ResponseEntity<List<OrderItem>>(list, new HttpHeaders(), HttpStatus.OK);
    }
	
	@GetMapping("/user")
    public List<OrderItem> getUserCart(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return user.getOrderItemList().stream().filter(orderItem -> orderItem.getOrder() == null).collect(Collectors.toList());
    }
	
	@PostMapping("/user")
	public ResponseEntity<List<OrderItem>> mergeUserCart(@RequestBody List<OrderItem> orderItems) {
		User user = userService.getCurrentLoggedUser();
	    try {
	    	orderItemService.mergeLocalCart(orderItems, user);
	    } catch (Exception e) {
	    	ResponseEntity.badRequest().body("Merge Cart Failed");
	    }
	    return ResponseEntity.ok(user.getOrderItemList());   
	}

 
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable("id") Long id) throws RecordNotFoundException {
        OrderItem entity = orderItemService.getOrderItemById(id);
        return new ResponseEntity<OrderItem>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping("/user/add")
    public boolean createOrUpdateOrderItem(@RequestBody OrderItem orderItem) throws RecordNotFoundException {
    	var item = itemService.getItemById(orderItem.getItem().getItemID());
        try {
        	ArrayList<OrderItem> list = new ArrayList<OrderItem>();
        	list.add(new OrderItem(orderItem.getQuantity(), item));
            mergeUserCart(list);
        } catch (Exception e) {
            return false;
        }
        return true;
    	
    }
    
//    @PostMapping
//    public ResponseEntity<OrderItem> createOrUpdateOrderItem(@RequestBody OrderItem orderItem) throws RecordNotFoundException {
//        OrderItem updated = orderItemService.createOrUpdateOrderItem(orderItem);
//        return new ResponseEntity<OrderItem>(updated, new HttpHeaders(), HttpStatus.OK);
//    }
 
    @DeleteMapping("/{id}")
    public HttpStatus deleteOrderItemById(@PathVariable("id") Long id) throws RecordNotFoundException {
        orderItemService.deleteOrderItemById(id);
        return HttpStatus.OK;
    }
}
