package js.RESTaurant.server.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Asset;


public interface IAssetService {
	List<Asset> getAllAssets();
	Page<Asset> getAllAssets(Pageable pageable);
	Asset getAssetById(Long id) throws RecordNotFoundException;
	Asset createOrUpdateAsset(Asset entity) throws RecordNotFoundException;
	Asset createMenuItemAsset(MultipartFile multipartFile);
	Asset updateMenuItemAsset(MultipartFile multipartFile, Asset asset);
	
	void deleteAssetById(Long id) throws RecordNotFoundException;
}
