package File.Sharing.platform.File.Sharing.Share;

import File.Sharing.platform.File.Sharing.MFile.MFile;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shareCode;

    private String shareToken;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private long downloadCount;

    private boolean active;

    @OneToOne
    @JoinColumn(name = "file_id", unique = true)
    private MFile MFile;




}
