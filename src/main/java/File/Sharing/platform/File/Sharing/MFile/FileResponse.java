package File.Sharing.platform.File.Sharing.MFile;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileResponse {

    private Long fid;
    private String originalFileName;
    private String type;
    private long size;
    private LocalDateTime uploadTime;
    private String storageFileName;
}