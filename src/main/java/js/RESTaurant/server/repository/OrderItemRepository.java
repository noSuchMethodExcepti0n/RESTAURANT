package js.RESTaurant.server.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import js.RESTaurant.server.model.OrderItem;

@RepositoryRestResource(collectionResourceRel = "orderitems", path = "orderitems")
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long> {

}