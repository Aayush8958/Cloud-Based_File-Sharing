package File.Sharing.platform.File.Sharing.Share;

import File.Sharing.platform.File.Sharing.MFile.MFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



import File.Sharing.platform.File.Sharing.MFile.MFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShareRepo extends JpaRepository<Share, Long> {

    Optional<Share> findByMFile(MFile MFile);

    Optional<Share> findByShareCode(String shareCode);

    Optional<Share> findByShareToken(String shareToken);
}