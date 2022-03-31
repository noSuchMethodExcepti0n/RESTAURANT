package js.RESTaurant.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import js.RESTaurant.server.exception.RecordNotFoundException;
import js.RESTaurant.server.model.Asset;
import js.RESTaurant.server.model.Item;
import js.RESTaurant.server.service.IAssetService;


@RestController
@RequestMapping("/assets") 
public class AssetController {

	@Autowired
	IAssetService assetService;
	
	
	@GetMapping
    public Page<Asset> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size) {
        
		PageRequest request = PageRequest.of(page - 1, size);
        return assetService.getAllAssets(request);
    }
	
//	@GetMapping
//    public ResponseEntity<List<Asset>> getAllAssets() {
//        List<Asset> list = assetService.getAllAssets();
//        return new ResponseEntity<List<Asset>>(list, new HttpHeaders(), HttpStatus.OK);
//    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Asset entity = assetService.getAssetById(id);
        return new ResponseEntity<Asset>(entity, new HttpHeaders(), HttpStatus.OK);
    }
 
    @PostMapping
    public ResponseEntity<Asset> createOrUpdateAsset(Asset asset) throws RecordNotFoundException {
        Asset updated = assetService.createOrUpdateAsset(asset);
        return new ResponseEntity<Asset>(updated, new HttpHeaders(), HttpStatus.OK);
    }
    
 
    @DeleteMapping("/{id}")
    public HttpStatus deleteAssetById(@PathVariable("id") Long id) throws RecordNotFoundException {
        assetService.deleteAssetById(id);
        return HttpStatus.FORBIDDEN;
    }
}
