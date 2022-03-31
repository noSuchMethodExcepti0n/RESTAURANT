package js.RESTaurant.server.service.impl;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Asset;
import js.RESTaurant.server.model.Item;
import js.RESTaurant.server.repository.ItemRepository;
import js.RESTaurant.server.service.IAssetService;
import js.RESTaurant.server.service.IItemService;
import js.RESTaurant.server.util.Utils;

@Service
public class ItemService implements IItemService {

	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	IAssetService assetService;
	
	@Value("${file.upload.directory}")
	private String uploadDir;
	
	public Page<Item> getAllItems(Pageable pageable) {
		return itemRepository.findAll(pageable);
    }
     
    public Item getItemById(Long id) throws RecordNotFoundException 
    {
        Optional<Item> item = itemRepository.findById(id);
         
        if(item.isPresent()) {
            return item.get();
        } else {
            throw new RecordNotFoundException("No item record exist for given id");
        }
    }
     
    public Item createOrUpdateItem(Item item) throws RecordNotFoundException {
    	if(item.getItemID() != null) {
    		 Optional<Item> itemFromDb = itemRepository.findById(item.getItemID());
            
            if(itemFromDb.isPresent()) {
            	 Item newEntity = itemFromDb.get();
                 newEntity.setActive(item.getActive());
                 newEntity.setAssetList(item.getAssetList());
                 newEntity.setDescription(item.getDescription());
                 newEntity.setName(item.getName());
                 //newEntity.setOrderItemList(entity.getOrderItemList());
                 newEntity.setType(item.getType());
                 newEntity.setValue(item.getValue());
      
                 newEntity = itemRepository.save(newEntity);
                  
                 return newEntity;
            } else {
            	item = itemRepository.save(item);
                return item;
            }
    	} else {
    		item = itemRepository.save(item);
            return item;
    	}
   
    } 
     
    public void deleteItemById(Long id) throws RecordNotFoundException {
        Optional<Item> item = itemRepository.findById(id);
         
        if(item.isPresent()) 
        {
            itemRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No item record exist for given id");
        }
    }

	@Override
	public Item createOrUpdateMenuItem(Item item, MultipartFile file) throws RecordNotFoundException {
		List<Asset> itemAssetList = null;
		Asset asset = null;
		if(item.getAssetList() != null && item.getAssetList().size() != 0) {
			itemAssetList = item.getAssetList().stream().filter(p -> p.getType().equals("menu_photo")).collect(Collectors.toList());
			asset = assetService.updateMenuItemAsset(file, itemAssetList.get(0));
		} else {
			asset = assetService.createMenuItemAsset(file);
		}
		
		Set<Asset> assetSet = new HashSet<Asset>();
		assetSet.add(asset);
		
		item.setAssetList(assetSet);
		
		return createOrUpdateItem(item);
	}

	@Override
	public Resource loadFile(String filename) {
		try {
			Path file = Paths.get(uploadDir).resolve(filename);
		    Resource resource = new UrlResource(file.toUri());

		    if (resource.exists() || resource.isReadable()) {
		    	return resource;
		    } else {
		    	throw new RuntimeException("Could not read the file!");
		    }
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public byte[] loadImage(String filename) {
		byte[] fileContent = Utils.convertFileToBytes(uploadDir, filename);
		return fileContent;
	}
	
	@Override
	public byte[] loadNoImageAvailable() {
		String noImageAvailableFilename = "no_image_available.png";
		byte[] fileContent = Utils.convertFileToBytes(uploadDir, noImageAvailableFilename);
		return fileContent;
	}

	@Override
	public Page<Item> getAllActiveItems(Pageable pageable) {
		return itemRepository.findByActiveTrue(pageable);
	}
	
}
