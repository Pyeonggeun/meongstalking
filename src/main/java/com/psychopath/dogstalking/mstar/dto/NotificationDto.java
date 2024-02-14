package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NotificationDto {
    private int notification_pk;
    private int user_pk;
    private String title;
    private String message;
    private String read_status;
    private String link_path;
    private String notify_img;
    private LocalDateTime created_at;
}
