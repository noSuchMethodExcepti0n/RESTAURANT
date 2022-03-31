package js.RESTaurant.server.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Order;
import js.RESTaurant.server.model.Role;
import js.RESTaurant.server.model.User;
import js.RESTaurant.server.repository.OrderRepository;
import js.RESTaurant.server.repository.UserRepository;
import js.RESTaurant.server.request.AuthRequest;
import js.RESTaurant.server.request.DashboardInformation;
import js.RESTaurant.server.response.JwtResponse;
import js.RESTaurant.server.security.MyUserDetailsService;
import js.RESTaurant.server.security.jwt.JwtUtil;
import js.RESTaurant.server.service.IRoleService;
import js.RESTaurant.server.service.IUserService;


@RestController
@RequestMapping("/users") 
@CrossOrigin(origins="http://localhost:4200")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@Autowired 
	JwtUtil jwtUtil;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	IRoleService roleService;

	
	@GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = userService.getAllUsers();
        return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) throws RecordNotFoundException {
        User entity = userService.getUserById(id);
        return new ResponseEntity<User>(entity, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) throws RecordNotFoundException {
        User entity = userService.getUserByUsername(username);
        return new ResponseEntity<User>(entity, new HttpHeaders(), HttpStatus.OK);
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest authenticationRequest) {
    	try {
    		authenticationManager.authenticate(
    				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
    		);
    	} catch (AuthenticationException e) {
    		e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    	final String token = jwtUtil.generateToken(userDetails);
    	final User user = userService.getUserByUsername(userDetails.getUsername());
    	
    	return ResponseEntity.ok(new JwtResponse(token, user.getUsername(), user.getRole().getName()));
    }
    
    @PostMapping("/checkUsername")
    public boolean isUsernameValid(@RequestBody String username) {
        return userService.checkIfUsernameUnique(username);
    }
    
    @PostMapping("/checkEmail")
    public boolean isEmailValid(@RequestBody String email) {
        return userService.checkIfEmailUnique(email);
    }
 
    @PostMapping
    public ResponseEntity<User> createOrUpdateUser(User user) throws RecordNotFoundException {
        User updated = userService.createOrUpdateUser(user);
        return new ResponseEntity<User>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public HttpStatus deleteUserById(@PathVariable("id") Long id) throws RecordNotFoundException {
        userService.deleteUserById(id);
        return HttpStatus.FORBIDDEN;
    }
    
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardInformation> getUserDashboard() throws RecordNotFoundException {
        DashboardInformation dashboardInformation = userService.getUserDashboardInformation();
        return new ResponseEntity<DashboardInformation>(dashboardInformation, new HttpHeaders(), HttpStatus.OK);
    }
    
}
