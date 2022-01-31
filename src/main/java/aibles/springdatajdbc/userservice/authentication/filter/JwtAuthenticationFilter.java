package aibles.springdatajdbc.userservice.authentication.filter;

import aibles.springdatajdbc.userservice.authentication.components.IJwtService;
import aibles.springdatajdbc.userservice.authentication.payload.CustomUserPrincipalService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    private final CustomUserPrincipalService customUserPrincipalService;
    private final IJwtService iJwtService;

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    public JwtAuthenticationFilter(CustomUserPrincipalService customUserPrincipalService, IJwtService iJwtService) {
        this.customUserPrincipalService = customUserPrincipalService;
        this.iJwtService = iJwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = getTokenFromHeader(request);
        final String username = getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserPrincipalService.loadUserByUsername(username);

            if (iJwtService.isValidToken(token, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest httpServletRequest){
        final String requestTokenHeader = httpServletRequest.getHeader(TOKEN_HEADER);

        if (requestTokenHeader != null && requestTokenHeader.startsWith(TOKEN_PREFIX)){
            return requestTokenHeader.substring(7);
        }
        else {
            logger.log(Level.WARNING, "Token does not sign with Bearer String.");
            return null;
        }
    }

    private String getUsernameFromToken(String token){
        String username = null;
        if (token != null){
            try {
                username = iJwtService.getUsernameFromToken(token);
            } catch (IllegalArgumentException ex){
                logger.warning("Unable to get JWT token");
            } catch (ExpiredJwtException ex){
                logger.warning("JWT token has expired");
            }
        }
        return username;
    }
}
