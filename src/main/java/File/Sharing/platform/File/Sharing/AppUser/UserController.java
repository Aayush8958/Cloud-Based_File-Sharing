package File.Sharing.platform.File.Sharing.AppUser;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Validated
@RestController
@AllArgsConstructor
public class UserController {
    private  final UserService userService;

    @GetMapping("/getByName")
    private ResponseEntity<UserDto> getByname(@RequestBody @Valid String username ){
        return new ResponseEntity<>(userService.getUserByUserName(username), HttpStatus.OK);
    }
    @GetMapping("/getByEmail")
    private ResponseEntity<UserDto> getByEmail(@RequestBody @Valid String email){
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }
    @DeleteMapping("/deleteByEmail")
    private ResponseEntity<String> deleteByemail(@RequestBody @Valid String email){
        return new ResponseEntity<>(userService.DeleteUserByEmail(email), HttpStatus.OK);
    }



}
