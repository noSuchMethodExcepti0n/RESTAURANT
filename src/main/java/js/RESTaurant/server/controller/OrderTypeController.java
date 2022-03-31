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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.OrderType;
import js.RESTaurant.server.service.IOrderTypeService;


@RestController
@RequestMapping("/orderTypes") 
public class OrderTypeController {

	@Autowired
	IOrderTypeService orderTypeService;

	
	@GetMapping
    public ResponseEntity<List<OrderType>> getAllOrderTypes() {
        List<OrderType> list = orderTypeService.getAllOrderTypes();
        return new ResponseEntity<List<OrderType>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<OrderType> getOrderTypeById(@PathVariable("id") Long id) throws RecordNotFoundException {
        OrderType entity = orderTypeService.getOrderTypeById(id);
        return new ResponseEntity<OrderType>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping
    public ResponseEntity<OrderType> createOrUpdateOrderType(OrderType employee) throws RecordNotFoundException {
        OrderType updated = orderTypeService.createOrUpdateOrderType(employee);
        return new ResponseEntity<OrderType>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public HttpStatus deleteOrderTypeById(@PathVariable("id") Long id) throws RecordNotFoundException {
        orderTypeService.deleteOrderTypeById(id);
        return HttpStatus.FORBIDDEN;
    }
}
