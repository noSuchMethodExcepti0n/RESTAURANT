package js.RESTaurant.server.enums;

public enum AssetType {
	MENU_PHOTO("menu_photo");
	
	private final String name;       

    private AssetType(String s) {
        name = s;
    }
    
    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false 
        return name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}
