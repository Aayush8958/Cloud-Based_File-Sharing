package File.Sharing.platform.File.Sharing.AppUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



import java.time.LocalTime;

@Entity
@Data
public class AppUser {
    @NotNull(message = "Email can't be left empty")
    @Id
    String email;
    @NotNull(message = "User name Cant't be left empty")
    String UserName;
    @NotNull(message = "Password Cant't be left empty")
    String Password;

    LocalTime createTime;

}
