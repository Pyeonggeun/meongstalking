package com.psychopath.dogstalking.trade.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ChatRoomDto {
    private int pk;
    private int article_pk;
    private int sender_pk;
    private int reciever_pk;
    private LocalDateTime created_at;

}
