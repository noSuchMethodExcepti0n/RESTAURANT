package js.RESTaurant.server.service;

import java.util.List;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Language;


public interface ILanguageService {
	List<Language> getAllLanguages();
	Language getLanguageById(Long id) throws RecordNotFoundException;
	Language createOrUpdateLanguage(Language entity) throws RecordNotFoundException;
	void deleteLanguageById(Long id) throws RecordNotFoundException;
}
