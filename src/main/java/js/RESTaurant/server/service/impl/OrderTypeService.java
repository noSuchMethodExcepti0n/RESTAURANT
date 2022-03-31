package js.RESTaurant.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.OrderType;
import js.RESTaurant.server.repository.OrderTypeRepository;
import js.RESTaurant.server.service.IOrderTypeService;

@Service
public class OrderTypeService implements IOrderTypeService {

	@Autowired
	OrderTypeRepository orderTypeRepository;
	
	public List<OrderType> getAllOrderTypes() {
        List<OrderType> orderTypeList = (List<OrderType>) orderTypeRepository.findAll();
         
        if(orderTypeList.size() > 0) {
            return orderTypeList;
        } else {
            return new ArrayList<OrderType>();
        }
    }
     
    public OrderType getOrderTypeById(Long id) throws RecordNotFoundException {
        Optional<OrderType> orderType = orderTypeRepository.findById(id);
         
        if(orderType.isPresent()) {
            return orderType.get();
        } else {
            throw new RecordNotFoundException("No orderType record exist for given id");
        }
    }
     
    public OrderType createOrUpdateOrderType(OrderType entity) throws RecordNotFoundException {
        Optional<OrderType> orderType = orderTypeRepository.findById(entity.getOrderTypeID());
         
        if(orderType.isPresent()) 
        {
            OrderType newEntity = orderType.get();
            newEntity.setName(entity.getName());
            newEntity.setOrderList(entity.getOrderList());
 
            newEntity = orderTypeRepository.save(newEntity);
             
            return newEntity;
        } else {
        	entity = orderTypeRepository.save(entity);
            return entity;
        }
    } 
     
    public void deleteOrderTypeById(Long id) throws RecordNotFoundException {
        Optional<OrderType> orderType = orderTypeRepository.findById(id);
         
        if(orderType.isPresent()) 
        {
            orderTypeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No orderType record exist for given id");
        }
    } 
    
    @Override
   	public OrderType getRoleByName(String name) throws RecordNotFoundException {
   		Optional<OrderType> orderType = orderTypeRepository.findByName(name);
           
           if(orderType.isPresent()) {
               return orderType.get();
           } else {
               throw new RecordNotFoundException("No orderType record exist for given name");
           }
   	}
}
