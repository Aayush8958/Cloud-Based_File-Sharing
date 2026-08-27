package File.Sharing.platform.File.Sharing.AppUser;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDateTime;


@Entity
@Data
public class AppUser {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Long id;
    @NotBlank(message = "Email can't be left empty")
    @Column(nullable = false, unique = true)
    String email;
    @NotNull(message = "User name can't be left empty")
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    @NotNull(message = "Password can't be left empty")
    String password;
    @Enumerated(EnumType.STRING)
    Role role;

    LocalDateTime createTime;

}
