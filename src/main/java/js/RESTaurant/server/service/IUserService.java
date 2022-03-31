package js.RESTaurant.server.service;

import java.util.List;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.User;
import js.RESTaurant.server.request.DashboardInformation;


public interface IUserService {
	List<User> getAllUsers();
	User getUserById(Long id) throws RecordNotFoundException;
	User createOrUpdateUser(User entity) throws RecordNotFoundException;
	User createUser(User entity);
	User save(User entity);
	void deleteUserById(Long id) throws RecordNotFoundException;
	
	User getUserByUsername(String username) throws RecordNotFoundException;
	
	boolean checkIfUsernameUnique(String username);
	boolean checkIfEmailUnique(String email);
	
	User getCurrentLoggedUser();
	DashboardInformation getUserDashboardInformation() throws RecordNotFoundException;
}
