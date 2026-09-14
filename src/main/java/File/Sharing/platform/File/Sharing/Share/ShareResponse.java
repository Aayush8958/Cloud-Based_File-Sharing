package File.Sharing.platform.File.Sharing.Share;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShareResponse {

    private String shareCode;
    private String shareToken;
    private String shareLink;
    private String qrCodeUrl;
    private LocalDateTime expiresAt;
    private boolean active;
}