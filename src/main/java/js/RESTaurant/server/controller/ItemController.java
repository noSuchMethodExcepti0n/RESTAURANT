package js.RESTaurant.server.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Item;
import js.RESTaurant.server.service.IItemService;
import js.RESTaurant.server.util.Utils;


@RestController
@RequestMapping("/items") 
@CrossOrigin(origins="http://localhost:4200")
public class ItemController {

	@Autowired
	IItemService itemService;

	@Value("${file.upload.directory}")
	private String uploadDir;
	
//	@GetMapping
//    public ResponseEntity<List<Item>> getAllItems() {
//        List<Item> list = itemService.getAllItems();
//        return new ResponseEntity<List<Item>>(list, new HttpHeaders(), HttpStatus.OK);
//    }
	
	@GetMapping
    public Page<Item> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "8") Integer size) {
        
		PageRequest request = PageRequest.of(page - 1, size);
        return itemService.getAllItems(request);
    }
	
	@GetMapping("/active")
    public Page<Item> findAllActive(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "8") Integer size) {
        
		PageRequest request = PageRequest.of(page - 1, size);
        return itemService.getAllActiveItems(request);
    }
 
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable("id") Long id) throws RecordNotFoundException {
        return itemService.getItemById(id);
    }
 
    @PostMapping
    public ResponseEntity<Item> createOrUpdateItem(@RequestBody Item item) throws RecordNotFoundException {
        Item updated = itemService.createOrUpdateItem(item);
        return new ResponseEntity<Item>(updated, new HttpHeaders(), HttpStatus.OK);
    }
    
    
    @RequestMapping(value="/upload", method=RequestMethod.POST, consumes={"multipart/form-data"} )
    @ResponseBody
    public ResponseEntity<Item> createOrUpdateItem(@RequestPart("file") MultipartFile file, @RequestPart("item") Item item){
    	 Item updated = itemService.createOrUpdateMenuItem(item, file);
         return new ResponseEntity<Item>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/image/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
    	byte[] fileContent = itemService.loadImage(filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileContent);
    }
 
    @DeleteMapping("/{id}")
    public HttpStatus deleteItemById(@PathVariable("id") Long id) throws RecordNotFoundException {
        itemService.deleteItemById(id);
        return HttpStatus.FORBIDDEN;
    }
    
//  do not remove    
//  @GetMapping("/files/{filename:.+}")
//  @ResponseBody
//  public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
//    Resource file = itemService.loadFile(filename);
//    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//  }
    
}
