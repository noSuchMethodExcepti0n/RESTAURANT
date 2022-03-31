package js.RESTaurant.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Role;
import js.RESTaurant.server.repository.RoleRepository;
import js.RESTaurant.server.service.IRoleService;

@Service
public class RoleService implements IRoleService {

	@Autowired
	RoleRepository roleRepository;
	
	public List<Role> getAllRoles() {
        List<Role> roleList = (List<Role>) roleRepository.findAll();
         
        if(roleList.size() > 0) {
            return roleList;
        } else {
            return new ArrayList<Role>();
        }
    }
     
    public Role getRoleById(Long id) throws RecordNotFoundException {
        Optional<Role> role = roleRepository.findById(id);
         
        if(role.isPresent()) {
            return role.get();
        } else {
            throw new RecordNotFoundException("No role record exist for given id");
        }
    }
     
    public Role createOrUpdateRole(Role entity) throws RecordNotFoundException {
        Optional<Role> role = roleRepository.findById(entity.getRoleID());
         
        if(role.isPresent()) {
            Role newEntity = role.get();
            newEntity.setName(entity.getName());
            newEntity.setUserList(entity.getUserList());
 
            newEntity = roleRepository.save(newEntity);
             
            return newEntity;
        } else {
        	entity = roleRepository.save(entity);
            return entity;
        }
    } 
     
    public void deleteRoleById(Long id) throws RecordNotFoundException {
        Optional<Role> role = roleRepository.findById(id);
         
        if(role.isPresent()) 
        {
            roleRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No role record exist for given id");
        }
    } 
    
    @Override
	public Role getRoleByName(String name) throws RecordNotFoundException {
		Optional<Role> role = roleRepository.findByName(name);
        
        if(role.isPresent()) {
            return role.get();
        } else {
            throw new RecordNotFoundException("No role record exist for given name");
        }
	}
}
