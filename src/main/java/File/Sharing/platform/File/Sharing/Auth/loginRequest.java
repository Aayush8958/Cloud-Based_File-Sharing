package File.Sharing.platform.File.Sharing.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class loginRequest {
    @NotBlank(message = "can't leave the password empty")
    String password;
    @NotBlank(message = "can't leave the email empty")
    String email;
}
