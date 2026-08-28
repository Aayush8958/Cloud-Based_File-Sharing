package File.Sharing.platform.File.Sharing.MFile;

import File.Sharing.platform.File.Sharing.AppUser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FileService {
    private final FileRepo repo;
    private final StorageService storage;

    public MFile upload(MultipartFile file, AppUser appUser) {
        if(file==null || file.isEmpty()){
            throw new RuntimeException("File cannot be empty");
        }
        String storageFileName=storage.store(file);
        MFile mfile =new MFile();
        mfile.setOriginalFileName(file.getOriginalFilename());
        mfile.setStorageFileName(storageFileName);
        mfile.setType(file.getContentType());
        mfile.setSize(file.getSize());
        mfile.setPath(storageFileName);
        mfile.setUploadTime(LocalDateTime.now());
        mfile.setAppUser(appUser);
        return repo.save(mfile);
    }
    public List<MFile> getUserFiles(
            AppUser appUser) {

        return repo.findByAppUser(appUser);
    }
    public MFile getFile(Long fid) {

        return repo.findById(fid)
                .orElseThrow(() ->
                        new RuntimeException("File not found"));
    }
    public Resource download(
            Long fid,
            AppUser appUser) {

        MFile mFile = getFile(fid);

        checkOwnership(mFile, appUser);

        return storage.load(
                mFile.getStorageFileName());
    }
    public void delete(
            Long fid,
            AppUser appUser) {

        MFile mFile = getFile(fid);

        checkOwnership(mFile, appUser);

        storage.delete(
                mFile.getStorageFileName()
        );

        repo.delete(mFile);
    }
    private void checkOwnership(
            MFile mFile,
            AppUser appUser) {

        if (!mFile.getAppUser()
                .getId()
                .equals(appUser.getId())) {

            throw new RuntimeException(
                    "You do not have permission to access this file"
            );
        }
    }


}
