package js.RESTaurant.server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
	
	private final String token;
	private final String username;
	private final String role;
}


