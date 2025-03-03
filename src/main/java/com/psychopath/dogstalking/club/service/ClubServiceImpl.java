package com.psychopath.dogstalking.club.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.club.dto.ClubArticleImageDto;
import com.psychopath.dogstalking.club.dto.ClubArticleImgDto;
import com.psychopath.dogstalking.club.dto.ClubDto;
import com.psychopath.dogstalking.club.dto.ClubFreeBoardDto;
import com.psychopath.dogstalking.club.dto.ClubImgBoardDto;
import com.psychopath.dogstalking.club.dto.ClubStatusLogDto;
import com.psychopath.dogstalking.club.dto.ClubUserDto;
import com.psychopath.dogstalking.club.dto.ClubUserRanklogDto;
import com.psychopath.dogstalking.club.dto.CommentDto;
import com.psychopath.dogstalking.club.dto.ImgCommentDto;
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

	public List<Map<String, Object>> selectImgFreeBoardAll(int pk) {
		return clubSqlMapper.selectImgFreeBoardAll(pk);
	}
    
	//게시판
	public void writeArticle(ClubFreeBoardDto clubFreeBoardDto) {
	
		clubSqlMapper.insertFreeBoard(clubFreeBoardDto);
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

	public int getLastInsertClubId(){
		return clubSqlMapper.getLastInsertClubId();
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

	public List<Map<String, Object>> selectRankClubList(){
		return clubSqlMapper.selectRankClubList();	
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
	
	public void insertClubUserRankTwo(ClubUserRanklogDto clubUserRanklogDto){
		clubSqlMapper.insertClubUserRankTwo(clubUserRanklogDto);
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

	public Integer selectClubCategoryPk(int club_user_pk){
		return clubSqlMapper.selectClubCategoryPk(club_user_pk);
	}

	public void writeImgArticle(ClubImgBoardDto clubImgBoardDto, List<ClubArticleImgDto> articleImageDtoList) {
		
		int articlePk = clubSqlMapper.createArticlePk();

		clubImgBoardDto.setClubimgboard_pk(articlePk);
		clubSqlMapper.insertClubImgFreeBoard(clubImgBoardDto);
		
		for(ClubArticleImgDto articleImageDto : articleImageDtoList) {
			articleImageDto.setClubimgboard_pk(articlePk);
			clubSqlMapper.insertClubImgFreeBoardImage(articleImageDto);
		}
	}

	public Map<String, Object> selectLatestPost(int club_pk){
		return clubSqlMapper.selectLatestPost(club_pk);	
	}

	public List<Map<String, Object>> selectLatestAlbum(int club_pk){
		return clubSqlMapper.selectLatestAlbum(club_pk);	
	}
	
	public List<Map<String, Object>> selectAlbumFreeBoard(int club_pk, int clubimgboard_pk){
		return clubSqlMapper.selectAlbumFreeBoard(club_pk,clubimgboard_pk);	
	}
	
	// 앨범 댓글
	public void writeImgComment(ImgCommentDto commentDto) {
		clubSqlMapper.insertImgComment(commentDto);
	}
	
	public List<Map<String, Object>> getImgCommentList(int articleId){
		return clubSqlMapper.selectImgCommentAll(articleId);	
	}

	public void updateImgComment(ImgCommentDto commentDto) {
		clubSqlMapper.updateImgComment(commentDto);
	}

	public void deleteImgComment(int commentId) {
		clubSqlMapper.deleteImgComment(commentId);
	}

	public void deleteComment(int commentId) {
		clubSqlMapper.deleteComment(commentId);
	}

	//메인페이지용
	public List<Map<String, Object>> selectFreeboardMainPage(int club_pk){
		return clubSqlMapper.selectFreeboardMainPage(club_pk);
	}

	public int isClubMember(int user_pk){
		return clubSqlMapper.isClubMember(user_pk);
	}

	//댓글 수
	public Integer countFreeboardComment(int comment_pk){
		return clubSqlMapper.countFreeboardComment(comment_pk);
	}

	public Integer countImgComment(int imgcomment_pk){
		return clubSqlMapper.countImgComment(imgcomment_pk);
	}

	public List<Map<String, Object>> searchMember(String name){
		return clubSqlMapper.searchMember(name);
	}

}

