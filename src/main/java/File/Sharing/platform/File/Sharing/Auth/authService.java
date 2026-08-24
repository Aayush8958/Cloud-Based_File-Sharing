package File.Sharing.platform.File.Sharing.Auth;

import File.Sharing.platform.File.Sharing.AppUser.AppUser;
import File.Sharing.platform.File.Sharing.AppUser.Role;
import File.Sharing.platform.File.Sharing.AppUser.UserRepo;
import File.Sharing.platform.File.Sharing.ExceptionHandling.AlreadyExisits;
import File.Sharing.platform.File.Sharing.ExceptionHandling.userNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Service
@AllArgsConstructor
public class authService {
 private final   UserRepo userRepo;


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
     String hashpw= BCrypt.hashpw(password, BCrypt.gensalt());
     AppUser appUser=new AppUser();
     appUser.setEmail(email);
     appUser.setPassword(hashpw);
     appUser.setUserName(username);
     appUser.setRole(Role.USER);
     userRepo.save(appUser);
     return "User created successfuly";
 }
// private authResponse login(loginRequest loginRequest) {
//  if(!userRepo.existsByEmail(loginRequest.getEmail())){
//   throw new userNotFound("Email not found");
//  }
//  AppUser appUser = userRepo.findByEmail(loginRequest.getEmail());
//  boolean pwdCheck= BCrypt.checkpw(loginRequest.getPassword(), appUser.getPassword());
//  if(!pwdCheck){
//   throw new userNotFound("Password is incorrect");
//  }
//
//  authResponse response=new authResponse("Loging Successful",);
//  return response;
 //}

}
