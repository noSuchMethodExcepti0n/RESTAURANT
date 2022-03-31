package js.RESTaurant.server.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Item;
import org.springframework.core.io.Resource;


public interface IItemService {
	Page<Item> getAllItems(Pageable pageable);
	Page<Item> getAllActiveItems(Pageable pageable);
	Item getItemById(Long id) throws RecordNotFoundException;
	Item createOrUpdateItem(Item entity) throws RecordNotFoundException;
	Item createOrUpdateMenuItem(Item item, MultipartFile file) throws RecordNotFoundException;
	void deleteItemById(Long id) throws RecordNotFoundException;
	Resource loadFile(String filename);
	
	byte[] loadImage(String filename);
	byte[] loadNoImageAvailable();
}
