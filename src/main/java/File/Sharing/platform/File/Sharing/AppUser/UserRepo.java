package File.Sharing.platform.File.Sharing.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<AppUser,Long> {

            boolean existsByUsername(String username);
            AppUser findByUsername(String username);

            boolean existsByEmail(String email);
            AppUser findByEmail(String email);
            boolean deleteByEmail(String email);
}
