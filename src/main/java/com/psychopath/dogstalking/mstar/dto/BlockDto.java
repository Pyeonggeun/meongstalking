package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BlockDto {
    private int block_pk;
    private int user_pk;
    private int block_user_pk;
    private LocalDateTime created_at;
}
