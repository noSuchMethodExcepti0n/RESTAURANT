package js.RESTaurant.server.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import js.RESTaurant.server.enums.OrderStatus;
import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Role;
import js.RESTaurant.server.model.User;
import js.RESTaurant.server.repository.UserRepository;
import js.RESTaurant.server.request.DashboardInformation;
import js.RESTaurant.server.service.IOrderService;
import js.RESTaurant.server.service.IRoleService;
import js.RESTaurant.server.service.IUserService;

@Service
public class UserService implements IUserService {

	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	IRoleService roleService;
	
	@Autowired
	IOrderService orderService;
	
	@Override
	public User getCurrentLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    User user = getUserByUsername(currentUserName);
		    
		    return user;
		}
		return null;
		
	}
	
	public List<User> getAllUsers()
    {
        List<User> userList = (List<User>) userRepository.findAll();
         
        if(userList.size() > 0) {
            return userList;
        } else {
            return new ArrayList<User>();
        }
    }
     
    public User getUserById(Long id) throws RecordNotFoundException 
    {
        Optional<User> user = userRepository.findById(id);
         
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new RecordNotFoundException("No user record exist for given id");
        }
    }
     
    public User createOrUpdateUser(User entity) throws RecordNotFoundException 
    {
        Optional<User> user = userRepository.findById(entity.getUserID());
         
        if(user.isPresent()) 
        {
            User newEntity = user.get();
            newEntity.setUsername(entity.getUsername());
            newEntity.setPassword(entity.getPassword());
            newEntity.setFirstname(entity.getFirstname());
            newEntity.setLastname(entity.getLastname());
            newEntity.setEmail(entity.getEmail());
            newEntity.setLanguage(entity.getLanguage());
            newEntity.setActive(true);
            newEntity.setLocked(entity.getLocked());
            newEntity.setOrderList(entity.getOrderList());
            
            Role role = roleService.getRoleByName("ROLE_CUSTOMER");
            newEntity.setRole(role);
 
            newEntity = userRepository.save(newEntity);
             
            return newEntity;
        } else {
        	entity = userRepository.save(entity);
            return entity;
        }
    } 
     
    public void deleteUserById(Long id) throws RecordNotFoundException 
    {
        Optional<User> user = userRepository.findById(id);
         
        if(user.isPresent()) 
        {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user record exist for given id");
        }
    }

	@Override
	public User getUserByUsername(String username) throws RecordNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
        
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new RecordNotFoundException("No user record exist for given id");
        }
	}

	@Override
	public User save(User entity) {
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return userRepository.save(entity);
	}

	@Override
	public boolean checkIfUsernameUnique(String username) {
		boolean usernameUnique = userRepository.existsByUsername(username);
		if(usernameUnique) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkIfEmailUnique(String email) {
		boolean emailUnique = userRepository.existsByEmail(email);
		if(emailUnique) {
			return false;
		}
		return true;
	}

	@Override
	public User createUser(User entity) {
		entity.setActive(true);
    	Role role = roleService.getRoleByName("ROLE_CUSTOMER");
    	entity.setRole(role);
		return save(entity);
	}

	@Override
	public DashboardInformation getUserDashboardInformation() throws RecordNotFoundException {
		User user = getCurrentLoggedUser();
		String roleName = user.getRole().getName();
		
		//begin and end of today day 
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date startDate = cal.getTime();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date endDate = cal.getTime();
		
		DashboardInformation dashboardInformation = new DashboardInformation();
		if(roleName.equalsIgnoreCase("ROLE_ADMIN")) {
			dashboardInformation.setInformation1(String.valueOf(orderService.getOrdersBetweenDate(startDate, endDate).size()));
			DecimalFormat df = new DecimalFormat("0 .00");
			dashboardInformation.setInformation2(df.format(orderService.countEarningsBetweenDate(startDate, endDate)));
			Role role = roleService.getRoleByName("ROLE_CUSTOMER");
			dashboardInformation.setInformation3(String.valueOf(userRepository.countByRole(role)));
		} else if(roleName.equalsIgnoreCase("ROLE_EMPLOYEE")) {
			dashboardInformation.setInformation1(String.valueOf(orderService.getOrdersBetweenDate(startDate, endDate).size()));
			dashboardInformation.setInformation2(orderService.countOrderByOrderStatus(OrderStatus.IN_PROGRESS).toString());
			dashboardInformation.setInformation3(orderService.countOrderByOrderStatus(OrderStatus.PENDING_FOR_PAYMENT).toString());
		} else {
			dashboardInformation.setInformation1(Integer.toString(user.getOrderList().size()));
			dashboardInformation.setInformation2(String.valueOf(user.getOrderList().stream().filter(order -> order.getStatus() == OrderStatus.IN_PROGRESS).count()));
			dashboardInformation.setInformation3(String.valueOf(user.getOrderItemList().stream().filter(orderItem -> orderItem.getOrder() == null).count()));
		}
		
		return dashboardInformation;
	}

}
