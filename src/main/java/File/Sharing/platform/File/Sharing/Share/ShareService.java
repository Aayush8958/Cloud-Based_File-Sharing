package File.Sharing.platform.File.Sharing.Share;

import File.Sharing.platform.File.Sharing.AppUser.AppUser;
import File.Sharing.platform.File.Sharing.MFile.FileRepo;
import File.Sharing.platform.File.Sharing.MFile.MFile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShareService {

    private final ShareRepo shareRepository;
    private final FileRepo mFileRepository;

    public ShareService(ShareRepo shareRepository,
                        FileRepo mFileRepository) {
        this.shareRepository = shareRepository;
        this.mFileRepository = mFileRepository;
    }

    public Share createShare(Long fid, AppUser appUser) {

        MFile file = mFileRepository.findById(fid)
                .orElseThrow(() ->
                        new RuntimeException("File not found"));

        if (!file.getAppUser().getId().equals(appUser.getId())) {
            throw new RuntimeException(
                    "You do not have permission to share this file");
        }

        Optional<Share> existingShare =
                shareRepository.findByMFile(file);

        if (existingShare.isPresent()) {

            Share share = existingShare.get();

            if (share.isActive()
                    && share.getExpiresAt()
                    .isAfter(LocalDateTime.now())) {

                return share;
            }

            share.setActive(false);
            shareRepository.save(share);
        }

        Share share = new Share();

        share.setShareCode(generateShareCode());
        share.setShareToken(UUID.randomUUID().toString());

        LocalDateTime now = LocalDateTime.now();

        share.setCreatedAt(now);
        share.setExpiresAt(now.plusHours(1));

        share.setDownloadCount(0);
        share.setActive(true);

        share.setMFile(file);

        return shareRepository.save(share);
    }

    private String generateShareCode() {

        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {

            int index =
                    (int) (Math.random() * characters.length());

            code.append(characters.charAt(index));
        }

        return code.toString();
    }

    public Share getValidShareByCode(String shareCode) {

        Share share = shareRepository
                .findByShareCode(shareCode)
                .orElseThrow(() ->
                        new RuntimeException("Invalid share code"));

        validateShare(share);

        return share;
    }

    public Share getValidShareByToken(String shareToken) {

        Share share = shareRepository
                .findByShareToken(shareToken)
                .orElseThrow(() ->
                        new RuntimeException("Invalid share link"));

        validateShare(share);

        return share;
    }

    private void validateShare(Share share) {

        if (!share.isActive()) {
            throw new RuntimeException(
                    "This share is no longer active");
        }

        if (share.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            share.setActive(false);
            shareRepository.save(share);

            throw new RuntimeException(
                    "This share link has expired");
        }
    }

    public void incrementDownloadCount(Share share) {

        share.setDownloadCount(
                share.getDownloadCount() + 1
        );

        shareRepository.save(share);
    }
}