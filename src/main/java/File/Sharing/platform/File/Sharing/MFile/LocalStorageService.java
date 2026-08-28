package File.Sharing.platform.File.Sharing.MFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
@Service
public class LocalStorageService implements StorageService{
    private final Path storageLocation;
    public LocalStorageService() {
        storageLocation = Paths.get("uploads").
                toAbsolutePath().normalize();
        try{
            Files.createDirectories(storageLocation);
        }catch(IOException e){
            throw new RuntimeException("Could not create the upload directory",e);
        }
    }
    @Override
    public String store(MultipartFile file) {
        String OriginalFilename= StringUtils.cleanPath(file.getOriginalFilename());
        String storageFileName= UUID.randomUUID()+"_"+OriginalFilename;
        try{
            Path targetLocation =storageLocation.resolve(storageFileName);

            try(InputStream inputStream=file.getInputStream()){
                Files.copy(inputStream,targetLocation,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return storageFileName;
        }catch(IOException e){
            throw new RuntimeException("Could not store file",e);
        }


    }

    @Override
    public Resource load(String fileName) {
        try{
            Path filePath=storageLocation.resolve(fileName).normalize();
            Resource resource=new UrlResource(filePath.toUri());
            if(resource.exists() && resource.isReadable()){
                return resource;
            }
            throw new RuntimeException("File not found"+fileName);
        }catch (Exception e){
            throw new RuntimeException("Could not load file",e);
        }

    }

    @Override
    public void delete(String fileName) {
try {
    Path filePath=storageLocation.resolve(fileName).normalize();
    Files.deleteIfExists(filePath);
} catch (IOException e) {
    throw new RuntimeException("Could not delete file",e);
}
    }
}
