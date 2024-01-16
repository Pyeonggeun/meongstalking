package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ArtPhotoDto {
    private int art_photo_pk;
    private int article_pk;
    private String photo_path;
    private int view_order;
    private LocalDateTime created_at;
}
