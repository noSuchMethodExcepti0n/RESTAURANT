package js.RESTaurant.server.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderStatus {
	@JsonProperty("PENDING FOR PAYMENT")
	PENDING_FOR_PAYMENT ("PENDING FOR PAYMENT"),
	@JsonProperty("IN PROGRESS")
	IN_PROGRESS ("IN PROGRESS"),
	@JsonProperty("COMPLEATED")
	COMPLEATED ("COMPLEATED"),
	@JsonProperty("CANCELLED")
	CANCELLED ("CANCELLED");
	
	private final String name;       

    private OrderStatus(String s) {
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
