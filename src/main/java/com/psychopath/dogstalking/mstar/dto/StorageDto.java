package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StorageDto {
    private int storage_pk;
    private int profile_info_pk;
    private String storage_name;
    private String storage_img;
    private LocalDateTime created_at;
}
