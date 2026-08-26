package File.Sharing.platform.File.Sharing.Security;

import File.Sharing.platform.File.Sharing.AppUser.AppUser;
import File.Sharing.platform.File.Sharing.AppUser.UserRepo;
import File.Sharing.platform.File.Sharing.ExceptionHandling.userNotFound;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    public UserDetails loadUserByUsername(String email)  {
        if(!userRepo.existsByEmail(email)) {
            throw new userNotFound("user name not found with email " + email);
        }
        AppUser user = userRepo.findByEmail(email);

        return new org.springframework.security.core.userdetails.User(
          user.getEmail(),
          user.getPassword(),
          List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()))
        );

    }
}
