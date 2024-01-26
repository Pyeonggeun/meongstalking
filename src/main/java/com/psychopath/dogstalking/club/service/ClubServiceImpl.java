package com.psychopath.dogstalking.club.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.club.dto.ClubDto;
import com.psychopath.dogstalking.club.dto.ClubFreeBoardDto;
import com.psychopath.dogstalking.club.dto.ClubStatusLogDto;
import com.psychopath.dogstalking.club.dto.ClubUserDto;
import com.psychopath.dogstalking.club.dto.ClubUserRanklogDto;
import com.psychopath.dogstalking.club.dto.CommentDto;
import com.psychopath.dogstalking.club.mapper.ClubSqlMapper;

@Service
public class ClubServiceImpl {

    @Autowired
	private ClubSqlMapper clubSqlMapper;
    
    public Map<String, Object> getloadSignupModal(int pk) {
		return (Map<String, Object>) clubSqlMapper.selectloadSignupModal(pk);

	}

    public List<Map<String, Object>> selectFreeBoardAll(int pk) {
		return clubSqlMapper.selectFreeBoardAll(pk);
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

	public void insertClubUsers(ClubUserDto clubUserDto) {
		clubSqlMapper.insertClubUsers(clubUserDto);
	}

	public int checka(){
		return clubSqlMapper.checka();
	}

	public int checkb(){
		return clubSqlMapper.checkb();
	}

	public int selectClubPK(int user_pk){
		return clubSqlMapper.selectClubPK(user_pk);
	}

	//리스트
	public List<Map<String, Object>> selectClubList(){
		return clubSqlMapper.selectClubList();	
	}

	public Map<String, Object> showclubpk(int club_pk){
		return clubSqlMapper.showclubpk(club_pk);	
	}
	
	public void insertUserStatusLog(ClubStatusLogDto clubStatusLogDto){
		clubSqlMapper.insertUserStatusLog(clubStatusLogDto);
	}

	public void insertLeader(ClubUserRanklogDto clubUserRanklogDto){
		clubSqlMapper.insertLeader(clubUserRanklogDto);
	}

	public List<Map<String, Object>> selectApplyList(int club_pk){
		return clubSqlMapper.selectApplyList(club_pk);	
	}

	public void updateApplyClub(ClubStatusLogDto clubStatusLogDto) {
		clubSqlMapper.updateApplyClub(clubStatusLogDto);
	}

	public int selectClubUserId(int pk){
		return clubSqlMapper.selectClubUserId(pk);
	}

	public void updatenotApplyClub(ClubStatusLogDto clubStatusLogDto) {
		clubSqlMapper.updatenotApplyClub(clubStatusLogDto);
	}

	public void updateClub(ClubDto clubDto) {
		clubSqlMapper.updateClub(clubDto);
	}
	
	public List<Map<String, Object>> selectMember(int club_pk){
		return clubSqlMapper.selectMember(club_pk);	
	}

	public void withdrawalClubUser(ClubStatusLogDto clubStatusLogDto) {
		clubSqlMapper.withdrawalClubUser(clubStatusLogDto);
	}

	public void insertClubUserRank(ClubUserRanklogDto clubUserRanklogDto){
		clubSqlMapper.insertClubUserRank(clubUserRanklogDto);
	}

	public Integer selectLeaderLank(int club_user_pk){
		return clubSqlMapper.selectLeaderLank(club_user_pk);
	}
	
	public void updateLeader(ClubUserRanklogDto clubUserRanklogDto) {
		clubSqlMapper.updateLeader(clubUserRanklogDto);
	}
}
