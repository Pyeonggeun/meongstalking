package com.psychopath.dogstalking.mstar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.psychopath.dogstalking.mstar.dto.ArtPhotoDto;
import com.psychopath.dogstalking.mstar.dto.ArticleDto;
import com.psychopath.dogstalking.mstar.dto.ProfileInfoDto;
import com.psychopath.dogstalking.mstar.dto.StorageDto;
import com.psychopath.dogstalking.mstar.dto.StoryDto;



@Mapper
public interface MstarSqlMapper{

    public void insertCommonInfo(ProfileInfoDto profileInfoDto);

    public ProfileInfoDto selectProfileInfoDto(int user_pk);

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

    public void insertStoryStroageDto(StorageDto storageDto);

    public void insertStoryDto(StoryDto storyDto);
}
