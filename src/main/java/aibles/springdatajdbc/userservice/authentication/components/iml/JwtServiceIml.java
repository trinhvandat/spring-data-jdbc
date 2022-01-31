package aibles.springdatajdbc.userservice.authentication.components.iml;

import aibles.springdatajdbc.userservice.authentication.components.IJwtService;
import aibles.springdatajdbc.userservice.user.models.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtServiceIml implements IJwtService {

    private static final long serialVersionUID = -2550185165626007488l;

    private static final long JWT_TOKEN_LIFE_TIME = 5*60*60;

    private static final String JWT_SECRET = "AIBLES_SECRET";

    @Override
    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());

        final Date issueAt = new Date(System.currentTimeMillis());
        final Date issueExpiration = new Date(System.currentTimeMillis() + JWT_TOKEN_LIFE_TIME * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issueAt)
                .setExpiration(issueExpiration)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    @Override
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isExpiratedToken(token);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFormToken(token, Claims::getSubject);
    }

    private boolean isExpiratedToken(final String token){
        return getExpirationDateFromToken(token).before(new Date());
    }

    private Date getExpirationDateFromToken(final String token){
        return getClaimFormToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFormToken(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(getClaimsFormToken(token));
    }

    private Claims getClaimsFormToken(final String token){
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

}
