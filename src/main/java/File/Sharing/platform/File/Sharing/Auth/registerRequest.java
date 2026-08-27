package File.Sharing.platform.File.Sharing.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class registerRequest {
    @NotBlank(message = "can't leave the username empty")
    String username;
    @NotBlank(message = "can't leave the password empty")
    String password;
    @NotBlank(message = "can't leave the email empty")
    String email;
}
