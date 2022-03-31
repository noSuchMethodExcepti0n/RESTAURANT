package js.RESTaurant.server.service.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import js.RESTaurant.server.enums.AssetType;
import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Asset;
import js.RESTaurant.server.repository.AssetRepository;
import js.RESTaurant.server.service.IAssetService;
import js.RESTaurant.server.util.Utils;

@Service
public class AssetService implements IAssetService {

	@Autowired
	AssetRepository assetRepository;
	
	@Value("${file.upload.directory}")
	private String uploadDir;
	
	public List<Asset> getAllAssets()
    {
        List<Asset> assetList = (List<Asset>) assetRepository.findAll();
         
        if(assetList.size() > 0) {
            return assetList;
        } else {
            return new ArrayList<Asset>();
        }
    }
	
	@Override
	public Page<Asset> getAllAssets(Pageable pageable) {
		return assetRepository.findAll(pageable);
    }
     
    public Asset getAssetById(Long id) throws RecordNotFoundException 
    {
        Optional<Asset> asset = assetRepository.findById(id);
         
        if(asset.isPresent()) {
            return asset.get();
        } else {
            throw new RecordNotFoundException("No asset record exist for given id");
        }
    }
     
    @Override
    public Asset createOrUpdateAsset(Asset asset) throws RecordNotFoundException {
    	if(asset.getAssetID() != null) {
    		Optional<Asset> assetFromDb = assetRepository.findById(asset.getAssetID());
            
            if(assetFromDb.isPresent()) {
                Asset newEntity = assetFromDb.get();
                newEntity.setExtension(asset.getExtension());
                newEntity.setFilename(asset.getFilename());
                //newEntity.setItemList(entity.getItemList());
                newEntity.setPath(asset.getPath());
                newEntity.setType(asset.getType());
     
                newEntity = assetRepository.save(newEntity);
                 
                return newEntity;
            } else {
            	asset = assetRepository.save(asset);
                return asset;
            }
    	} else {
    		asset = assetRepository.save(asset);
            return asset;
    	}
    } 
    
    @Override
    public Asset createMenuItemAsset(MultipartFile multipartFile) {
    	Utils.createDirIfNotExists(uploadDir);
    	Path filePath = Utils.saveFileOnDisk(uploadDir, multipartFile); 
    	
    	String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
    	
    	Asset asset = new Asset();
    	asset.setFilename(filePath.getFileName().toString());
    	asset.setExtension(extension);
    	asset.setPath(filePath.toString());
    	asset.setType(AssetType.MENU_PHOTO.toString());
    	
    	return assetRepository.save(asset);
    }
    
    public boolean checkIfFileExists(String filename) {
    	Path filepath = Paths.get(uploadDir.toString(), filename);
    	File f = new File(filepath.toString());
    	if(f.exists() && !f.isDirectory()) { 
    	    return true;
    	}
    	return false;
    }
    
    @Override
    public Asset updateMenuItemAsset(MultipartFile multipartFile, Asset asset) {
    	Utils.createDirIfNotExists(uploadDir);
    	//save only if not exists!
    	
    	if(!checkIfFileExists(multipartFile.getOriginalFilename())) {
    		Path filePath = Utils.saveFileOnDisk(uploadDir, multipartFile); 
    		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        	
        	asset.setFilename(filePath.getFileName().toString());
        	asset.setExtension(extension);
        	asset.setPath(filePath.toString());
        	asset.setType(AssetType.MENU_PHOTO.toString());
    	}
    	
    	
    	return assetRepository.save(asset);
    }

    @Override
    public void deleteAssetById(Long id) throws RecordNotFoundException {
        Optional<Asset> asset = assetRepository.findById(id);
         
        if(asset.isPresent()) {
            assetRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No asset record exist for given id");
        }
    } 
    
}
