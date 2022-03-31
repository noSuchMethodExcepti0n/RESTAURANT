package js.RESTaurant.server.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import js.RESTaurant.server.model.OrderType;
import js.RESTaurant.server.model.Role;

@RepositoryRestResource(collectionResourceRel = "ordertypes", path = "ordertypes")
public interface OrderTypeRepository extends PagingAndSortingRepository<OrderType, Long> {
	Optional<OrderType> findByName(String name);
}