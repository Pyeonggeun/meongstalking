package com.psychopath.dogstalking.club.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.club.dto.ClubDto;
import com.psychopath.dogstalking.club.dto.ClubFreeBoardDto;
import com.psychopath.dogstalking.club.dto.ClubStatusLogDto;
import com.psychopath.dogstalking.club.dto.ClubUserDto;
import com.psychopath.dogstalking.club.dto.ClubUserRanklogDto;
import com.psychopath.dogstalking.club.dto.CommentDto;


@Mapper
public interface ClubSqlMapper {
    
    public int createArticlePk();   
    public void insertFreeBoard(ClubFreeBoardDto clubFreeBoardDto);

    public ClubFreeBoardDto selectById(int id);

    public Map<String, Object> selectloadSignupModal(int s);
    public List<Map<String, Object>> selectFreeBoardAll(int s);

    // 댓글
	public void insertComment(CommentDto commentDto);
    public List<Map<String, Object>> selectCommentAll(int article_id);

    //개설
    public void insertClub(ClubDto clubDto);
    public void insertClubUser(ClubUserDto clubUserDto);
    public void insertClubUsers(ClubUserDto clubUserDto);
    
    //가입
    public Map<String, Object> applyClubUserTF(int user_pk);
    public int checka();
    public int checkb();
    public int selectClubPK(int user_pk);

    //리스트
    public List<Map<String, Object>> selectClubList();
    public Map<String, Object> showclubpk(int club_pk);

    //회원상태
    public void insertUserStatusLog(ClubStatusLogDto clubStatusLogDto);

    //리더 위임
    public void insertLeader(ClubUserRanklogDto clubUserRanklogDto);

    //신청 리스트
    public List<Map<String, Object>> selectApplyList(int club_pk);

    //가입상태
    public void updateApplyClub(ClubStatusLogDto clubStatusLogDto);
    public void updatenotApplyClub(ClubStatusLogDto clubStatusLogDto);
    public int selectClubUserId(int s);
    public void withdrawalClubUser(ClubStatusLogDto clubStatusLogDto);

    //길드 수정
    public void updateClub(ClubDto clubDto);

    //길드 멤버
    public List<Map<String, Object>> selectMember(int club_pk);

}
