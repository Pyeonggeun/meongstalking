package com.psychopath.dogstalking.mstar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.mstar.dto.ArtPhotoDto;
import com.psychopath.dogstalking.mstar.dto.ArticleDto;
import com.psychopath.dogstalking.mstar.dto.ArticleLikeDto;
import com.psychopath.dogstalking.mstar.dto.ArticleScrapDto;
import com.psychopath.dogstalking.mstar.dto.ArticleTagDto;
import com.psychopath.dogstalking.mstar.dto.BlockDto;
import com.psychopath.dogstalking.mstar.dto.BookmarkDto;
import com.psychopath.dogstalking.mstar.dto.CmtLikeDto;
import com.psychopath.dogstalking.mstar.dto.CommentDto;
import com.psychopath.dogstalking.mstar.dto.DirectDto;
import com.psychopath.dogstalking.mstar.dto.FollowDto;
import com.psychopath.dogstalking.mstar.dto.NotificationDto;
import com.psychopath.dogstalking.mstar.dto.ProfileInfoDto;
import com.psychopath.dogstalking.mstar.dto.StorageDto;
import com.psychopath.dogstalking.mstar.dto.StoryDto;
import com.psychopath.dogstalking.mstar.dto.StorySaveDto;
import com.psychopath.dogstalking.mstar.dto.TcommentDto;
import com.psychopath.dogstalking.mstar.dto.ViewStoryDto;



@Mapper
public interface MstarSqlMapper{

    public void insertCommonInfo(ProfileInfoDto profileInfoDto);

    public void insertBlockDto(BlockDto blockDto);
    public void deleteUserBlock(BlockDto blockDto);

    public int selectBlockStatus(@RequestParam("user_pk") int user_pk,
                                @RequestParam("block_user_pk")int block_user_pk);

    public void insertBookMarkDto(BookmarkDto bookmarkDto);
    public void deleteUserBookMark(BookmarkDto bookmarkDto);

    public int selectBookMarkStatus(@RequestParam("profile_info_pk") int bkm_user_pk,
                                    @RequestParam("another_info_pk")int rcv_user_pk);
    

    public ProfileInfoDto selectProfileInfoDto(int user_pk);
    public ProfileInfoDto selectProfileInfoDtoByPIP(int profile_info_pk);

    public UserDto selectUserDto(int user_pk);
    public UserDto selectUserDtoByPIP(int profile_info_pk);

    public int selectMaxProfilePk();
    public void updateFirstNickName(int profile_info_pk, String nick_name);

    public int selectArticleCount(int user_info_pk);

    public int selectFollowingCount(int user_info_pk);

    public int selectFollowCount(int user_info_pk);

    public void updateProfileInfoDto(ProfileInfoDto profileInfoDto);

    public void insertArticle(ArticleDto articleDto);

    public int selectMaxArticlePk();

    public void insertArticlePhoto(ArtPhotoDto artPhotoDto);

    public List<ArticleDto> selectMyArticleDtoList(int profile_info_pk);

    public List<ArtPhotoDto> selectArticlePhotoList(int article_pk);

    public List<StorageDto> selectStoryStorageList(int profile_info_pk);

    public int selectStoryCount(int profile_info_pk);

    public void insertStoryStroageDto(StorageDto storageDto);
    public int selectStoragePk();

    public List<StoryDto> selectStorageStoryList(int storage_pk);
    public StorageDto selectStorageDto(int storage_pk);

    public void insertSaveStory(StorySaveDto storySaveDto);

    public void insertStoryDto(StoryDto storyDto);

    public List<ArticleDto> selectArticleList();

    public List<ArticleDto> selectProfileArticleList(int profile_info_pk);
    public List<ArticleDto> selectMyScrapArticleDtoList(int profile_info_pk);

    public void deleteArticleScrap(ArticleScrapDto articleScrapDto);
    public void insertArticleScrap(ArticleScrapDto articleScrapDto);

    public int selectScrapStatus(@RequestParam("article_pk") int article_pk,
                                 @RequestParam("profile_info_pk")int profile_info_pk);

    public void insertUserFollowing(FollowDto followDto);
    public void deleteUserFollowing(FollowDto followDto);

    public int checkFollowStatus(@RequestParam("following_user_pk") int following_user_pk,
                                 @RequestParam("follow_user_pk")int follow_user_pk);
                                                              
    public int checkFollowingStatus(@RequestParam("following_user_pk") int following_user_pk,
                                   @RequestParam("follow_user_pk")int follow_user_pk);
    public List<UserDto> selectFollowingUserList(@RequestParam("profile_info_pk") int following_user_pk,
                                                 @RequestParam("following_user_pk")int follow_user_pk,
                                                 @RequestParam("searchText")String searchText);
                                                        
    public List<UserDto> selectFollowUserList(@RequestParam("profile_info_pk")int follow_user_pk,
                                                    @RequestParam("follow_user_pk") int following_user_pk,
                                                    @RequestParam("searchText")String searchText);
    public List<UserDto> selectMyFollowList(int profile_info_pk, String searchText);
    public List<UserDto> selectMyFollowingList(int profile_info_pk, String searchText);
                                                    
    public List<UserDto> selectFollowFollowingUserList(@RequestParam("profile_info_pk")int profile_info_pk,
                                                    @RequestParam("another_info_pk") int another_info_pk,
                                                    @RequestParam("searchText")String searchText);
                                                        
    public void insertArticleComment(CommentDto commentDto);

    public void insertArticleTcomment(TcommentDto tCommentDto);

    public int selectArticleCommentCount(int article_pk);

    public List<CommentDto> selectArticleCommentList(int article_pk);
    public int selectTotalTcommentCount(int article_pk);
    
    public int selectTcommentCount(int article_pk);
    public List<TcommentDto> selectTcommentList(int comment_pk);

    public int selectCommentLike(int comment_pk);

    public int selectCommentLikeCount(int comment_pk);
    public int selectWriterLikeComment(@RequestParam("comment_pk") int comment_pk,
                                       @RequestParam("profile_info_pk")int profile_info_pk);

    public int selectWriterProfilePkByCommnet(int comment_pk);


    public int selectWriterProfilePk(int article_pk);

    public void insertCommentLike(CmtLikeDto cmtLikeDto);
    public void deleteCommentLike(CmtLikeDto cmtLikeDto);

    public CommentDto selectInsertedMyComment(int profile_info_pk);

    public void insertArticleLike(ArticleLikeDto articleLikeDto);
    public void deleteArticleLike(ArticleLikeDto articleLikeDto);

    public int checkMyArticleLikeCount(@RequestParam("article_pk") int article_pk,
                                  @RequestParam("profile_info_pk")int profile_info_pk);

    public int selectTcommentCountBycommentPk(int comment_pk);
    
    public int selectArticleLikeCount(int article_pk);
    public List<ProfileInfoDto> selectProfileInfoArticleLikeAndFollow(@RequestParam("article_pk") int article_pk,
                                                                      @RequestParam("profile_info_pk")int profile_info_pk);

    public int[] selectStoryList(int profile_info_pk);

    public List<StoryDto> selectUserStoryList(int profile_info_pk);
    public List<StoryDto> selectMyStoryList(int profile_info_pk);

    public StoryDto selectStoryDto(int story_pk);

    public void insertViewStoryDto(ViewStoryDto viewStoryDto);
    public int checkStoryViewStatus(ViewStoryDto viewStoryDto);
     

    public int selectStoryViewCount(int story_pk);
    public List<ProfileInfoDto> selectStoryViewProfileInfo(int story_pk);

    public int selectViewStoryStatus(@RequestParam("story_pk") int story_pk,
                                     @RequestParam("myProfile_info_pk")int myProfile_info_pk);

    public int[] selectMyDirectList(int profile_info_pk);

    public DirectDto selectLastDirectDto(@RequestParam("another_info_pk") int another_info_pk,
                                         @RequestParam("profile_info_pk")int profile_info_pk);
                                         
    public List<DirectDto> selectDirectChatList(@RequestParam("another_info_pk") int another_info_pk,
                                         @RequestParam("profile_info_pk")int profile_info_pk);

    public List<DirectDto> selectNewChatList(@RequestParam("direct_pk") int direct_pk,
                                            @RequestParam("another_info_pk") int another_info_pk,
                                            @RequestParam("profile_info_pk")int profile_info_pk);
                                            
    public int selectChangeDirectStatus(@RequestParam("direct_pk") int direct_pk,
                                                    @RequestParam("another_info_pk") int another_info_pk,
                                                    @RequestParam("profile_info_pk")int profile_info_pk);
    public void updateReadStatus(@RequestParam("profile_info_pk")int profile_info_pk,
                                @RequestParam("another_info_pk") int another_info_pk);

    public List<UserDto> selectSearchUserList(@RequestParam("profile_info_pk") int profile_info_pk,
                                            @RequestParam("search_text") String search_text);
    public int[] selectMyFollowingUserFollowing(@RequestParam("another_info_pk") int another_info_pk,
                                                @RequestParam("profile_info_pk")int profile_info_pk);

    public void insertDirectChat(DirectDto directDto);                                                          

    public List<StoryDto> selectMyAllStoryList(int profile_info_pk);

    public List<NotificationDto> selectUnReadNotificationList(int user_pk);
    public List<NotificationDto> selectReadNotificationList(int user_pk);
    public int selectUnReadNotify(int user_pk);
    public int selectUnReadDirect(int user_pk);
    
    public void updateNotificationStatus(int user_pk);
    
    public int selectMaxArticlePhotoPk();
    public void insertArticleTag(ArticleTagDto articleTagDto);
    public List<ArticleTagDto> selectArticleTagList(int art_photo_pk);

    public List<ProfileInfoDto> selectRecoUserList(int user_pk);
}
