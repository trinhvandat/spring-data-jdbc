package aibles.springdatajdbc.userservice.authentication.payload;

import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserPrincipalService implements UserDetailsService {

    private final IUserInfoRepository iUserInfoRepository;

    @Autowired
    public CustomUserPrincipalService(IUserInfoRepository iUserInfoRepository) {
        this.iUserInfoRepository = iUserInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserInfoRepository.retrieveUserByUsername(username)
                .map(userInfo -> {
                    return new CustomUserPrincipal(userInfo.getUsername(), userInfo.getPassword());
                })
                .orElseThrow(()-> {
                    throw new UsernameNotFoundException("User not found with username" + username);
                });
    }
}
