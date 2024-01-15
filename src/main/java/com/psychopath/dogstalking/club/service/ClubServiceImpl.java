package com.psychopath.dogstalking.club.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.club.dto.ClubDto;
import com.psychopath.dogstalking.club.dto.ClubFreeBoardDto;
import com.psychopath.dogstalking.club.dto.ClubUserDto;
import com.psychopath.dogstalking.club.dto.CommentDto;
import com.psychopath.dogstalking.club.mapper.ClubSqlMapper;

@Service
public class ClubServiceImpl {

    @Autowired
	private ClubSqlMapper clubSqlMapper;
    
    public Map<String, Object> getloadSignupModal(int pk) {
		return (Map<String, Object>) clubSqlMapper.selectloadSignupModal(pk);

	}

    public List<Map<String, Object>> selectFreeBoardAll() {
		return clubSqlMapper.selectFreeBoardAll();
	}
    
	//게시판
	public void writeArticle(ClubFreeBoardDto clubFreeBoardDto) {
		
		//int articlePk = clubSqlMapper.createArticlePk();
		//, List<ClubArticleImageDto> clubArticleImageDto

		//clubFreeBoardDto.setClubfreeboard_pk(articlePk);
		clubSqlMapper.insertFreeBoard(clubFreeBoardDto);
		/* 
		for(ArticleImageDto articleImageDto : articleImageDtoList) {
			articleImageDto.setArticle_id(articlePk);
			guestBookSqlMapper.insertImage(articleImageDto);
		}*/
	}

	// 댓글
	public void writeComment(CommentDto commentDto) {
		clubSqlMapper.insertComment(commentDto);
	}
	
	public List<Map<String, Object>> getCommentList(int articleId){
		return clubSqlMapper.selectCommentAll(articleId);	
	}

	//가입
	public Map<String, Object> applyClubUserTF(int user_pk){
		return clubSqlMapper.applyClubUserTF(user_pk);	
	};


	//길드 개설
	public void insertClub(ClubDto clubDto) {
		clubSqlMapper.insertClub(clubDto);
	}

	public void insertClubUser(ClubUserDto clubUserDto) {
		clubSqlMapper.insertClubUser(clubUserDto);
	}
	
}
