package js.RESTaurant.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Language;
import js.RESTaurant.server.repository.LanguageRepository;
import js.RESTaurant.server.service.ILanguageService;

@Service
public class LanguageService implements ILanguageService {

	@Autowired
	LanguageRepository languageRepository;
	
	public List<Language> getAllLanguages()
    {
        List<Language> languageList = (List<Language>) languageRepository.findAll();
         
        if(languageList.size() > 0) {
            return languageList;
        } else {
            return new ArrayList<Language>();
        }
    }
     
    public Language getLanguageById(Long id) throws RecordNotFoundException 
    {
        Optional<Language> language = languageRepository.findById(id);
         
        if(language.isPresent()) {
            return language.get();
        } else {
            throw new RecordNotFoundException("No language record exist for given id");
        }
    }
     
    public Language createOrUpdateLanguage(Language entity) throws RecordNotFoundException 
    {
        Optional<Language> language = languageRepository.findById(entity.getLanguageID());
         
        if(language.isPresent()) 
        {
            Language newEntity = language.get();
            newEntity.setName(entity.getName());
            newEntity.setShortname(entity.getShortname());
            newEntity.setUserList(entity.getUserList());
            
            newEntity = languageRepository.save(newEntity);
             
            return newEntity;
        } else {
        	entity = languageRepository.save(entity);
            return entity;
        }
    } 
     
    public void deleteLanguageById(Long id) throws RecordNotFoundException 
    {
        Optional<Language> language = languageRepository.findById(id);
         
        if(language.isPresent()) 
        {
            languageRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No language record exist for given id");
        }
    } 
}
