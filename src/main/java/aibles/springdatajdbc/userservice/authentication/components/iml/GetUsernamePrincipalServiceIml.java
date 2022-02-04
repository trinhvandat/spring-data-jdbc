package aibles.springdatajdbc.userservice.authentication.components.iml;

import aibles.springdatajdbc.userservice.authentication.components.IGetUsernamePrincipalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class GetUsernamePrincipalServiceIml implements IGetUsernamePrincipalService {

    @Override
    public String execute() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
    }
}
