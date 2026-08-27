package File.Sharing.platform.File.Sharing.Auth;

import File.Sharing.platform.File.Sharing.AppUser.AppUser;
import File.Sharing.platform.File.Sharing.AppUser.Role;
import File.Sharing.platform.File.Sharing.AppUser.UserRepo;
import File.Sharing.platform.File.Sharing.ExceptionHandling.AlreadyExisits;
import File.Sharing.platform.File.Sharing.ExceptionHandling.userNotFound;
import File.Sharing.platform.File.Sharing.Security.CustomUserDetailsService;
import File.Sharing.platform.File.Sharing.Security.JwtService;
import File.Sharing.platform.File.Sharing.Security.PasswordConfig;
import File.Sharing.platform.File.Sharing.Security.PasswordConfig.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class authService {
 private final   UserRepo userRepo;
private final PasswordConfig passwordConfig;
private final JwtService jwtService;
private final CustomUserDetailsService customUserDetailsService;

 String register(registerRequest registerRequest) {
     if(userRepo.existsByEmail(registerRequest.getEmail())){
        throw new AlreadyExisits("Email already exists");
     }
     if(userRepo.existsByUsername(registerRequest.getUsername())){
         throw new AlreadyExisits("Username already exists");
     }
     String username = registerRequest.getUsername();
     String password = registerRequest.getPassword();
     String email = registerRequest.getEmail();
     String hashpw= passwordConfig.passwordEncoder().encode(password);
     AppUser appUser=new AppUser();
     appUser.setEmail(email);
     appUser.setPassword(hashpw);
     appUser.setUsername(username);
     appUser.setRole(Role.USER);
     appUser.setCreateTime(LocalDateTime.now());
     userRepo.save(appUser);
     return "User created successfuly";
 }
 authResponse login(loginRequest loginRequest) {
  if(!userRepo.existsByEmail(loginRequest.getEmail())){
   throw new userNotFound("Email not found");
  }
  AppUser appUser = userRepo.findByEmail(loginRequest.getEmail());
  boolean pwdCheck= BCrypt.checkpw(loginRequest.getPassword(), appUser.getPassword());
  if(!pwdCheck){
   throw new userNotFound("Password is incorrect");
  }
     UserDetails userDetails= new org.springframework.security.core.userdetails.User(
             appUser.getEmail(),
             appUser.getPassword(),
             List.of(new SimpleGrantedAuthority("ROLE_"+appUser.getRole().name()))
     );
  String token= jwtService.generateToken(userDetails);

  return new authResponse("Login Successful",token);

 }

}
