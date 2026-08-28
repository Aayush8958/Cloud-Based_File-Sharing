package File.Sharing.platform.File.Sharing.MFile;

import File.Sharing.platform.File.Sharing.AppUser.AppUser;
import File.Sharing.platform.File.Sharing.Share.Share;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class MFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long fid;
    String OriginalFileName;
    String StorageFileName;
    String Type;
    long size;
    String Path;
    LocalDateTime uploadTime;

    @ManyToOne(fetch = FetchType.LAZY)
    AppUser appUser;
    @OneToOne(cascade = CascadeType.ALL)
    Share share;

}
