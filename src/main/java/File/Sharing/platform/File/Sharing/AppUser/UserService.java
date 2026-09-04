package File.Sharing.platform.File.Sharing.AppUser;

import File.Sharing.platform.File.Sharing.ExceptionHandling.userNotFound;
import org.springframework.stereotype.Service;

@Service
public class UserService {
UserRepo userRepo;
Todto convert;

    public UserService(Todto covert, UserRepo userRepo) {
        this.convert = covert;
        this.userRepo = userRepo;
    }

    UserDto getUserByUserName(String username) {
        if (!userRepo.existsByUsername(username)) {
            throw new userNotFound("User not found");
        }
return convert.toUserDto(userRepo.findByUsername(username));
    }
    public AppUser getUserByEmail(String email){
        if(!userRepo.existsByEmail(email)){
            throw new userNotFound("Email not found");
        }
        return userRepo.findByEmail(email);
    }

   public UserDto getUserByEmailDto(String email){
        if(!userRepo.existsByEmail(email)){
            throw new userNotFound("Email not found");
        }
        return convert.toUserDto(userRepo.findByEmail(email));
    }
    String DeleteUserByEmail(String email){
        if(!userRepo.existsByEmail(email)){
            throw new userNotFound("Email not found");
        }
        boolean confirm=userRepo.deleteByEmail(email);
        if(confirm)
        return "User deleted successfully";

        return "not able to delete user";
    }
//    String UpdateProfileByEmail(String email){
//        if(!userRepo.existsByEmail(email)){
//            throw new userNotFound("Email not found");
//        }
//    }

}
