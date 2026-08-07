package File.Sharing.platform.File.Sharing.Share;

import File.Sharing.platform.File.Sharing.MFile.MFile;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String shareCode;
    LocalTime CreateTime;
    LocalTime ExpireAt;
    boolean active;
    @OneToOne(cascade = CascadeType.ALL)
    MFile mFile;




}
