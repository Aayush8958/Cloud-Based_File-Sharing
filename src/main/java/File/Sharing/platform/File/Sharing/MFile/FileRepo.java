package File.Sharing.platform.File.Sharing.MFile;

import File.Sharing.platform.File.Sharing.AppUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
 interface FileRepo extends JpaRepository<MFile,Long> {
    List<MFile> findByAppUser(AppUser appUser);
}
