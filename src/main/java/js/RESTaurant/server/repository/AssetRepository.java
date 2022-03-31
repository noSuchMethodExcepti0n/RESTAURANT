package js.RESTaurant.server.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import js.RESTaurant.server.model.Asset;

@RepositoryRestResource(collectionResourceRel = "assets", path = "assets")
public interface AssetRepository extends PagingAndSortingRepository<Asset, Long> {

}
