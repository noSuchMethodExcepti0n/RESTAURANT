package js.RESTaurant.server.request;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class ItemForm {
	private String itemID;
	private String name;
	private String type;
	private String description;
	private String value;
	private boolean active; 
	private MultipartFile file;
	
	
	public ItemForm() {
	}
	
	public ItemForm(String itemID, String name, String type, String description, String value, boolean active,
			MultipartFile file) {
		super();
		this.itemID = itemID;
		this.name = name;
		this.type = type;
		this.description = description;
		this.value = value;
		this.active = active;
		this.file = file;
	}
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
