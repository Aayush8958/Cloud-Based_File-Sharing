package File.Sharing.platform.File.Sharing.MFile;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String store(MultipartFile file);

    Resource load(String fileName);

    void delete(String fileName);

}
