package com.psychopath.dogstalking.follow.dto;

import lombok.Data;
import java.time.*;

@Data
public class CollectionDto {
    private int collection_pk;
    private int user_pk;
    private int log_pk;
    private LocalDateTime created_date;
}
