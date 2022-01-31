package aibles.springdatajdbc.userservice.authentication.components;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {

    String generateJwtToken(final UserDetails userDetails);

    boolean isValidToken(final String token, UserDetails userDetails);

    String getUsernameFromToken(final String token);
}
