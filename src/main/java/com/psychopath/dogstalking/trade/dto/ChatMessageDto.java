package com.psychopath.dogstalking.trade.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatMessageDto {
    private int pk;
    private int chatroom_pk;
    private int user_pk;
    private String message;
    private String isRead;
    private LocalDateTime created_at;

}
