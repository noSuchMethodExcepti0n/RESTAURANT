package js.RESTaurant.server.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import js.RESTaurant.server.model.Language;

@RepositoryRestResource(collectionResourceRel = "languages", path = "languages")
public interface LanguageRepository extends PagingAndSortingRepository<Language, Long> {

}
