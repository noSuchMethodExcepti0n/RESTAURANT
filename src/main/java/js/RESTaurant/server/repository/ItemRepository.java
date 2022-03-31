package js.RESTaurant.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import js.RESTaurant.server.model.Item;
import js.RESTaurant.server.model.Order;
import js.RESTaurant.server.model.User;

@RepositoryRestResource(collectionResourceRel = "items", path = "items")
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
	Page<Item> findByActiveTrue(Pageable pageable);
}