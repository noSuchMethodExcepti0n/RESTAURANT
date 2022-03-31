package js.RESTaurant.server.service;

import java.util.List;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Role;


public interface IRoleService {
	List<Role> getAllRoles();
	Role getRoleById(Long id) throws RecordNotFoundException;
	Role createOrUpdateRole(Role entity) throws RecordNotFoundException;
	void deleteRoleById(Long id) throws RecordNotFoundException;
	
	Role getRoleByName(String name) throws RecordNotFoundException;
}
