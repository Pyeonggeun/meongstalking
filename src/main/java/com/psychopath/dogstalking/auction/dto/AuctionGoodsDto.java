package com.psychopath.dogstalking.auction.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class AuctionGoodsDto {
    private int pk;
    private int category_pk;
    private int user_pk;
    private int starting_price;
    private int bin_price;
    private LocalDateTime commencement_date;
    private LocalDateTime expiry_date;
    private String image_link;
    private String title;
    private String content;
    private int read_count;
    private LocalDateTime created_at;
}
