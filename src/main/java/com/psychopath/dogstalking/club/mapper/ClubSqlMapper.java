package com.psychopath.dogstalking.club.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.club.dto.ClubDto;
import com.psychopath.dogstalking.club.dto.ClubFreeBoardDto;
import com.psychopath.dogstalking.club.dto.ClubUserDto;
import com.psychopath.dogstalking.club.dto.CommentDto;


@Mapper
public interface ClubSqlMapper {
    
    public int createArticlePk();   
    public void insertFreeBoard(ClubFreeBoardDto clubFreeBoardDto);

    public ClubFreeBoardDto selectById(int id);

    public Map<String, Object> selectloadSignupModal(int s);
    public List<Map<String, Object>> selectFreeBoardAll();

    // 댓글
	public void insertComment(CommentDto commentDto);
    public List<Map<String, Object>> selectCommentAll(int article_id);

    //개설
    public void insertClub(ClubDto clubDto);
    public void insertClubUser(ClubUserDto clubUserDto);
    
    //가입
    public Map<String, Object> applyClubUserTF(int user_pk);
    public int checka();

    //리스트
    public List<Map<String, Object>> selectClubList();
    public Map<String, Object> showclubpk(int club_pk);

}
