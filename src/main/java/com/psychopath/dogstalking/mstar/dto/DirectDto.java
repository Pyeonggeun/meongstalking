package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class DirectDto {
    private int direct_pk;
    private int send_user_pk;
    private int rcv_user_pk;
    private String chat;
    private String read_status;
    private LocalDateTime created_at;
}
