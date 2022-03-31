package js.RESTaurant.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import js.RESTaurant.server.enums.OrderStatus;
import js.RESTaurant.server.model.Order;
import js.RESTaurant.server.model.User;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
	Page<Order> findByUser(Pageable pageable, User user);
	
	@Query("select count(o) from Order o where o.createdAt between :startDate AND :endDate ")
	long countTodayOrders(Date startDate, Date endDate);
	
	List<Order> findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(Date startDate, Date endDate);
	Long countByStatus(OrderStatus status);
}