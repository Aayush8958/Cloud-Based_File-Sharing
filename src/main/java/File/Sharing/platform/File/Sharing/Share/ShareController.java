package File.Sharing.platform.File.Sharing.Share;

import File.Sharing.platform.File.Sharing.AppUser.AppUser;
import File.Sharing.platform.File.Sharing.AppUser.UserService;
import File.Sharing.platform.File.Sharing.MFile.MFile;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import File.Sharing.platform.File.Sharing.MFile.MFile;
import File.Sharing.platform.File.Sharing.MFile.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/share")
public class ShareController {

    private final ShareService shareService;
    private final UserService appUserService;
    private final StorageService storageService;
    private final QRCodeService qrCodeService;

    public ShareController(ShareService shareService, UserService appUserService, StorageService storageService, QRCodeService qrCodeService) {
        this.shareService = shareService;
        this.appUserService = appUserService;
        this.storageService = storageService;
        this.qrCodeService = qrCodeService;
    }



    @PostMapping("/{fid}")
    public ShareResponse createShare(
            @PathVariable Long fid,
            Authentication authentication) {

        String email = authentication.getName();

        AppUser appUser =
                appUserService.getUserByEmail(email);

        Share share =
                shareService.createShare(fid, appUser);

        String shareLink =
                "http://192.168.1.14:9090/share/link/"
                        + share.getShareToken();

        String qrCodeUrl =
                "http://192.168.1.14:9090/share/qr/"
                        + share.getShareToken();

        ShareResponse response = new ShareResponse();

        response.setShareCode(share.getShareCode());
        response.setShareToken(share.getShareToken());
        response.setShareLink(shareLink);
        response.setQrCodeUrl(qrCodeUrl);
        response.setExpiresAt(share.getExpiresAt());
        response.setActive(share.isActive());

        return response;
    }
    @GetMapping("/code/{shareCode}")
    public ResponseEntity<Resource> downloadByCode(
            @PathVariable String shareCode) {

        Share share = shareService.getValidShareByCode(shareCode);

        MFile file = share.getMFile();

        Resource resource = storageService.load(file.getStorageFileName());
        shareService.incrementDownloadCount(share);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                file.getOriginalFileName() + "\""
                )
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        file.getType()
                )
                .body(resource);
    }
    @GetMapping("/link/{shareToken}")
    public ResponseEntity<Resource> downloadByToken(
            @PathVariable String shareToken) {

        Share share =
                shareService.getValidShareByToken(shareToken);

        MFile file = share.getMFile();

        Resource resource =
                storageService.load(file.getStorageFileName());

        shareService.incrementDownloadCount(share);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                file.getOriginalFileName() + "\""
                )
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        file.getType()
                )
                .body(resource);
    }

    @GetMapping("/qr/{shareToken}")
    public ResponseEntity<byte[]> generateQRCode(
            @PathVariable String shareToken) {

        Share share =
                shareService.getValidShareByToken(shareToken);

        String shareLink =
                "http://192.168.1.14:9090/share/link/"
                        + share.getShareToken();

        byte[] qrCode =
                qrCodeService.generateQRCode(
                        shareLink,
                        300,
                        300
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        "image/png"
                )
                .body(qrCode);
    }
}