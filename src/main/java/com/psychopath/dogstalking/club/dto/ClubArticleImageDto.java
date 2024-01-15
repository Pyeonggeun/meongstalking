package com.psychopath.dogstalking.club.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClubArticleImageDto {
	private int clubfreeboardimg_pk;
	private int clubfreeboard_pk;
	private String location;
	private String original_filename;
	private LocalDateTime created_at;
}