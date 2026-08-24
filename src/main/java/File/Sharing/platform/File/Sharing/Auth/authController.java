package File.Sharing.platform.File.Sharing.Auth;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Validated
@AllArgsConstructor
public class authController {
    private final authService auth;


    @PostMapping("SignUp")
    ResponseEntity<String>  RegisterUser(@RequestBody @Valid registerRequest registerRequest) {
        return ResponseEntity.ok(auth.register(registerRequest));
    }
//    ResponseEntity<authResponse>  Login(@RequestBody @Valid loginRequest loginRequest) {
//        return  ResponseEntity.ok()
//    }
}
