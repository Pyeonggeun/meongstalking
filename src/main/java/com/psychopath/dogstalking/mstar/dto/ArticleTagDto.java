package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ArticleTagDto {
    private int tag_pk;
    private int tag_info_pk;
    private int art_photo_pk;
    private double x_coordinates;
    private double y_coordinates;
    private LocalDateTime created_at;
}
