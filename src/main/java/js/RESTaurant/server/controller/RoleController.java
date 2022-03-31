package js.RESTaurant.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Role;
import js.RESTaurant.server.service.IRoleService;


@RestController
@RequestMapping("/roles") 
public class RoleController {

	@Autowired
	IRoleService roleService;

	
	@GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> list = roleService.getAllRoles();
        return new ResponseEntity<List<Role>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Role entity = roleService.getRoleById(id);
        return new ResponseEntity<Role>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping
    public ResponseEntity<Role> createOrUpdateRole(Role employee) throws RecordNotFoundException {
        Role updated = roleService.createOrUpdateRole(employee);
        return new ResponseEntity<Role>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public HttpStatus deleteRoleById(@PathVariable("id") Long id) throws RecordNotFoundException {
        roleService.deleteRoleById(id);
        return HttpStatus.FORBIDDEN;
    }
}
