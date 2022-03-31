package js.RESTaurant.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Language;
import js.RESTaurant.server.service.ILanguageService;


@RestController
@RequestMapping("/languages") 
public class LanguageController {

	@Autowired
	ILanguageService languageService;

	
	@GetMapping
    public ResponseEntity<List<Language>> getAllLanguages() {
        List<Language> list = languageService.getAllLanguages();
        return new ResponseEntity<List<Language>>(list, new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Language entity = languageService.getLanguageById(id);
        return new ResponseEntity<Language>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping
    public ResponseEntity<Language> createOrUpdateLanguage(Language employee) throws RecordNotFoundException {
        Language updated = languageService.createOrUpdateLanguage(employee);
        return new ResponseEntity<Language>(updated, new HttpHeaders(), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public HttpStatus deleteLanguageById(@PathVariable("id") Long id) throws RecordNotFoundException {
        languageService.deleteLanguageById(id);
        return HttpStatus.FORBIDDEN;
    }
}
