package File.Sharing.platform.File.Sharing.MFile;

import File.Sharing.platform.File.Sharing.AppUser.AppUser;
import File.Sharing.platform.File.Sharing.AppUser.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService mFileService;
    private final UserService appUserService;

    public FileController(
            FileService mFileService,
            UserService appUserService) {
        this.mFileService = mFileService;
        this.appUserService = appUserService;
    }

    @PostMapping("/upload")
    public ResponseEntity<MFile> upload(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        String email =
                authentication.getName();

        AppUser appUser =
                appUserService.getUserByEmail(email);

        MFile mFile =
                mFileService.upload(
                        file,
                        appUser
                );

        return ResponseEntity.ok(mFile);
    }

    @GetMapping
    public ResponseEntity<List<MFile>> getMyFiles(
            Authentication authentication) {

        String email =
                authentication.getName();

        AppUser appUser =
                appUserService.getUserByEmail(email);

        return ResponseEntity.ok(
                mFileService.getUserFiles(appUser)
        );
    }

    @GetMapping("/{fid}/download")
    public ResponseEntity<Resource> download(
            @PathVariable Long fid,
            Authentication authentication) {

        String email =
                authentication.getName();

        AppUser appUser =
                appUserService.getUserByEmail(email);

        MFile mFile =
                mFileService.getFile(fid);

        Resource resource =
                mFileService.download(
                        fid,
                        appUser
                );

        MediaType mediaType;

        try {

            mediaType =
                    MediaType.parseMediaType(
                            mFile.getType()
                    );

        } catch (Exception e) {

            mediaType =
                    MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                mFile.getOriginalFileName() +
                                "\""
                )
                .body(resource);
    }

    @DeleteMapping("/{fid}")
    public ResponseEntity<String> delete(
            @PathVariable Long fid,
            Authentication authentication) {

        String email =
                authentication.getName();

        AppUser appUser =
                appUserService.getUserByEmail(email);

        mFileService.delete(
                fid,
                appUser
        );

        return ResponseEntity.ok(
                "File deleted successfully"
        );
    }
}
