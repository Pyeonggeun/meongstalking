package com.psychopath.dogstalking.club.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.club.dto.ClubArticleImgDto;
import com.psychopath.dogstalking.club.dto.ClubDto;
import com.psychopath.dogstalking.club.dto.ClubFreeBoardDto;
import com.psychopath.dogstalking.club.dto.ClubImgBoardDto;
import com.psychopath.dogstalking.club.dto.ClubStatusLogDto;
import com.psychopath.dogstalking.club.dto.ClubUserDto;
import com.psychopath.dogstalking.club.dto.ClubUserRanklogDto;
import com.psychopath.dogstalking.club.dto.CommentDto;
import com.psychopath.dogstalking.club.dto.ImgCommentDto;


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
    public int getLastInsertClubId();

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
    public List<Map<String, Object>> selectRankClubList();
    

    //회원상태
    public void insertUserStatusLog(ClubStatusLogDto clubStatusLogDto);

    //리더 위임
    public void insertLeader(ClubUserRanklogDto clubUserRanklogDto);

    //회원 등급
    public void insertClubUserRank(ClubUserRanklogDto clubUserRanklogDto);

    public void insertClubUserRankTwo(ClubUserRanklogDto clubUserRanklogDto);

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

    public Integer selectLeaderLank(int club_user_pk);

    //관리자 바꿈
    public void updateLeader(ClubUserRanklogDto clubUserRanklogDto);

    //회원 승인 미승인 상태
    public Integer selectClubCategoryPk(int club_user_pk);


    public void insertClubImgFreeBoard(ClubImgBoardDto clubImgBoardDto);
    public void insertClubImgFreeBoardImage(ClubArticleImgDto articleImageDto);
    public List<Map<String, Object>> selectImgFreeBoardAll(int s);

    //최근게시물
    public Map<String, Object> selectLatestPost(int club_pk);

    //최신 앨범
    public List<Map<String, Object>> selectLatestAlbum(int s);

    public List<Map<String, Object>> selectAlbumFreeBoard(int club_pk, int clubimgboard_pk);

    //앨범 댓글
	public void insertImgComment(ImgCommentDto commentDto);
    public List<Map<String, Object>> selectImgCommentAll(int article_id);
    public void updateImgComment(ImgCommentDto commentDto);
    public void deleteImgComment(int id);

    //메인페이지
    public List<Map<String, Object>> selectFreeboardMainPage(int club_pk);
    

    public int isClubMember(int user_pk);

    //댓글 수
    public Integer countImgComment(int imgcomment_pk);
    public Integer countFreeboardComment(int comment_pk);

    //멤버검색
    public List<Map<String, Object>> searchMember(String name);
       
}
