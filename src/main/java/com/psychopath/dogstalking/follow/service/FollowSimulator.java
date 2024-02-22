package com.psychopath.dogstalking.follow.service;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.commons.mapper.UserSqlMapper;
import com.psychopath.dogstalking.commons.service.UserServiceImpl;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.follow.dto.CollectionDto;
import com.psychopath.dogstalking.follow.dto.CommentDto;
import com.psychopath.dogstalking.follow.dto.LogDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;
import com.psychopath.dogstalking.follow.mapper.FollowSqlMapper;

@Service
public class FollowSimulator {

    final int DAILY_MARKING_COUNT = 80;

    final int MIN_USER_PK = 51; //51번 회원
    final int MAX_USER_PK = 100; //100번 회원 - 75번 회원 기준 앞뒤로 가장 많이 활동

    @Autowired
    private UserSqlMapper userSqlMapper;

    @Autowired
    private FollowSqlMapper followSqlMapper;

    @Autowired
    private UserServiceImpl userService;

    public void register(){

        String [] namePieces = {"김","이","하","후","류","하","나","조","인","춘","김","태","호","배","진","우","임","나","연","심","진","용"};

        for(int i = 0; i < 50; i++){

            long rValue = (long)(Math.random() * 100000000000000000L);
            String userid = "R" + rValue;

            int nameRange = (int)(Math.random() * 3) + 2; 

            String name = "";

            for(int x = 0 ; x < nameRange ; x++){
                name += namePieces[(int)(Math.random()*namePieces.length)];
            }

            String [] genderPieces = {"M", "F"};
            String gender = genderPieces[(int)(Math.random()*genderPieces.length)];


            UserDto user = new UserDto();
            user.setUserid(userid);
            user.setName(name);
            user.setGender(gender);


            user.setUserpw("1111");
            user.setEmail("1111");
            user.setBirthdate(LocalDate.now());
            user.setPhone("1111");
            user.setImage("1111");

            // userSqlMapper.insertUser(user);
            userService.register_user(user);

            // 추가 정보
            int userpk = userSqlMapper.selectUserPk();

            UserMoreDto userMoreDto = new UserMoreDto();

            userMoreDto.setUser_pk(userpk);
            userMoreDto.setHeight((int)(Math.random()*40 + 160));
            userMoreDto.setWeight((int)(Math.random()*60 + 40));
            userMoreDto.setMbti_type("ENFP");
            userMoreDto.setBlood_type((int)(Math.random()*2) > 0 ? "A" : "B");
            userMoreDto.setDrinking((int)(Math.random()*2) > 0 ? "Y" : "N");
            userMoreDto.setSmoking((int)(Math.random()*2) > 0 ? "Y" : "N");
            userMoreDto.setHobby("없음");
            userMoreDto.setPreference((int)(Math.random()*2) > 0 ? "M" : "W");

            followSqlMapper.insertMoreInfo(userMoreDto);


        }


    }

    // 마킹
    @Scheduled(cron = "27 * * * * *")
    public void marking(){

        if((int)(Math.random()*1440) < DAILY_MARKING_COUNT) { 
            // 사람 선택 (50명 중에서 30~80)
            Random random = new Random();
            int userPk = (int)(random.nextGaussian()*((MAX_USER_PK-MIN_USER_PK)/2)/3) + (MIN_USER_PK + MAX_USER_PK)/2; // 45는 중앙 번호

            // System.out.println("으앙: " + userPk);

            if(userPk >= MIN_USER_PK && userPk <= MAX_USER_PK) {
                // 자취를 남긴다...

                String[] imageList = {
                    "2024/02/21/9895cd32-80f5-43b5-aad7-791f3e1ee9d4_1708509448774.jpeg",
                    "2024/02/20/7c2bd4ef-ad57-4de0-8c3d-87881e37d540_1708421578383.jpeg",
                    "2024/02/20/61e8a907-5526-41bd-a618-02970c9d3811_1708416402757.jpg",
                    "2024/02/20/652302ae-f8f9-485d-81c1-23d974a9a99d_1708415246278.jpeg",
                    "2024/02/21/917c489e-38bb-4280-a4d5-6aae97c0b031_1708510203811.jpeg",
                    "2024/02/21/48912a98-262e-427d-9b9b-70c025a7e089_1708510224903.jpeg",
                    "2024/02/21/ae4356a2-1f83-486d-8937-e39c861814c8_1708510357791.jpeg",
                    "2024/02/21/9eca2a54-9646-4f77-ada9-511fa5b89ae1_1708510389052.jpeg",
                    "2024/02/22/2ed434e7-7966-4478-9590-d8fa8b00d4b1_1708598170819.jpg",
                    "2024/02/22/d6111ab9-3c51-48ef-95af-40a4bad5511e_1708598212315.jpg",
                    "2024/02/22/7d876059-a047-42b5-b693-fcbb0c7ecec6_1708598301423.jpg",
                    "2024/02/22/d64f7b76-27b5-4bbd-bb95-f1fc771e2dba_1708598341694.jpg",
                    "2024/02/22/25ba5671-a5c2-4ecf-ba4d-b66d3da14292_1708598391713.jpg",
                    "2024/02/22/3156cf8a-eee7-463e-b592-cedf188b70cd_1708598437381.jpg",
                    "2024/02/22/33affb20-144f-40a2-9952-2e2f0e69104f_1708598497897.jpg",
                    "2024/02/22/a8f09b21-3807-4a9f-ba4c-308e718c8f34_1708598532128.jpg",
                };

                String[] contentList = {
                    "아침 햇살에 웃음 지어요",
                    "눈 내리는 창밖, 따뜻한 차 한 잔",
                    "책과 함께하는 평화로운 오후 ",
                    "친구들과 함께하는 웃음이 넘치는 순간",
                    "일몰을 바라보며 감사한 마음으로 ",
                    "비오는 날, 창가에 앉아 차 한 잔",
                    "산책하는 동안 듣는 음악에 흥이 솟아요..",
                    "집에서 즐기는 영화 시간, 따뜻한 이불과 함께.",
                    "오랜만에 만나는 친구들과의 끝없는 대화..",
                    "바다 소리를 들으며 여유로운 휴식..",
                    "새로운 요리 도전, 맛있는 결과물 만들기",
                    "꽃 향기 가득한 봄날, 산책하는 기분.",
                    "선물 받은 작은 행복, 감사한 마음으로 기억하기 ..",
                    "열정적인 일의 성취감, 자신감에 가득 차서..",
                    "어릴 적 추억 속 노래, 감성을 되새기며..",
                    "일몰을 바라보며 소중한 사람과 함께하는 시간..",
                    "자연 속에서의 휴식, 신선한 공기 마시며.",
                    "열정적인 취미 활동으로 마음을 달래기",
                    "간만에 만나는 가족들과의 특별한 순간",
                    "변화하는 계절을 담은 사진, 자연의 아름다움",
                    "심심한 날, 새로운 취미 발견하고 시도하기",
                    "산책로에서 마주친 귀여운 동물들에 감동",
                    "가슴 뭉클한 추억 속 사진들을 되새기며..",
                    "일상 속 작은 기쁨들에 마음을 열고 기록하기",
                    "꿈꾸던 여행지를 상상하며 여행 계획 세우기",
                };

                LogDto logDto = new LogDto();
                logDto.setUser_pk(userPk);
                logDto.setImage_link(imageList[(int)(Math.random()*imageList.length)]);
                logDto.setContent(contentList[(int)(Math.random()*contentList.length)]);

                //위도는 37.58536437574824 이고, 경도는 126.9684233228641 입니다
                //클릭한 위치의 위도는 37.4716142577263 이고, 경도는 127.19203257577148 입니다

                double staticLat = 37.4996259;
                double staticLng = 127.0304801;
    
                switch ((int)(Math.random()*4)) {
                    case 0, 3:
                        // 강남 학원 위치
                        staticLat = 37.4996259;
                        staticLng = 127.0304801;
                        break;
                    case 1:
                        // 송파 어딘가 위치
                        staticLat = 37.50795753686061;
                        staticLng = 127.09994223756871;
                        break;

                    case 2:
                        // 송파 어딘가 위치
                        staticLat = 37.501385094767755;
                        staticLng = 127.11440920893604;
                        break;
                }

                final double RANGE_LATITUDE = 0.01;
                final double RANGE_LONGITUDE = 0.01;

                double randomLatitude = staticLat  + Math.random()*(RANGE_LATITUDE*2) - RANGE_LATITUDE;
                double randomLongitude = staticLng  + Math.random()*(RANGE_LONGITUDE*2) - RANGE_LONGITUDE;

                logDto.setLatitude(randomLatitude);
                logDto.setLongitude(randomLongitude);

                followSqlMapper.insertWriteTrackMarkInfo(logDto);

            }


        }
    }


    // 수집


    @Scheduled(cron = "27 46 5 * * *")
    public void collect(){
        
        System.out.println("괜찮아 토닥토닥...3");

        for(int i = 0 ; i < (DAILY_MARKING_COUNT/2) ; i++){

            try{
                int userPk = (int)(Math.random()*(MAX_USER_PK - MIN_USER_PK)) + MIN_USER_PK;
                Integer logPk = followSqlMapper.getRandomLogPkExceptMine(userPk);

                if(logPk == null) {
                    System.out.println("괜찮아 토닥토닥...2");
                    continue;
                }

                CollectionDto params = new CollectionDto();
                params.setUser_pk(userPk);
                params.setLog_pk(logPk);

                followSqlMapper.insertCollectionInfo(params);

                String[] commentList = {
                    "멋진 사진! 항상 응원해",
                    "너무 멋져! 계속해서 활동해",
                    "사랑스러운 모습이야! 파이팅",
                    "힘내! 넌 정말 대단해",
                    "자랑스럽다! 항상 응원할게",
                    "너무 멋져서 자랑스러워",
                    "이뤄내는 모습이 멋져! 계속해서 화이팅",
                    "끝까지 응원할게! 화이팅",
                    "멋진 모습이야! 항상 응원해",
                    "너의 열정이 인상적이야! 파이팅",
                    "너무 멋져! 이뤄낼 거야 ",
                    "너의 노력을 칭찬해! 파이팅",
                    "항상 뒤에서 응원할게! 화이팅",
                    "멋진 사진과 함께한 순간! 파이팅",
                    "자랑스러워! 항상 응원할게",
                    "너의 노력에 박수를 보내요! 화이팅",
                    "멋진 사진! 항상 응원해",
                    "자랑스러운 모습이야! 파이팅",
                    "너의 노력에 박수를 보내요! 화이팅",
                    "멋진 사진! 항상 응원할게",
                    "너무 멋져! 계속해서 활동해",
                    "사랑스러운 모습이야! 파이팅",
                    "힘내! 넌 정말 대단해",
                };


                if(Math.random()*100 < 70){
                    CommentDto commentDto = new CommentDto();
                    commentDto.setUser_pk(userPk);
                    commentDto.setLog_pk(userPk);
                    commentDto.setContent(commentList[(int)(Math.random()*commentList.length)]);
    
                    followSqlMapper.insertComment(commentDto);
                }



            }catch(Exception e){
                System.out.println("괜찮아 토닥토닥...");
            }

        }

    }



    // 로그 지우기(하루 한번 - 2주 전 내용들은 지운다...)
    @Scheduled(cron = "52 13 5 * * *")
    public void initLogsForTest(){

        // System.out.println("무섭당");

        followSqlMapper.deleteLog();
        followSqlMapper.deleteComment();
        followSqlMapper.deleteCollection();
        followSqlMapper.deleteLikeLog();
        followSqlMapper.deleteLikeUserColletZero();

        // ... 
    }




    // // @Scheduled(cron = "0 0 0,3,6,9,12,15,18,21 * * *")
    // public void marking(){
    //     // 매일 마킹을 한다...

    //     for(int x = 0; x < 500; x++){
    //         int userpk = (int)(Math.random()*400 + 4300);

    //         LogDto logDto = new LogDto();
    //         logDto.setUser_pk(userpk);
    //         logDto.setImage_link("2024/01/221f3d8671-527b-4367-866b-3efa12f0b596_1705906413210.webp");
    //         logDto.setContent(userpk + ": 시뮬레이터 입니다.");

    //         //위도는 37.58536437574824 이고, 경도는 126.9684233228641 입니다
    //         //클릭한 위치의 위도는 37.4716142577263 이고, 경도는 127.19203257577148 입니다
    //         double latitudeRange = 37.58536437574824 - 37.4716142577263;
    //         double longitudeRange = 127.19203257577148 - 126.9684233228641;

    //         double randomLatitude = Math.random()*latitudeRange  + 37.4716142577263;
    //         double randomLongitude = Math.random()*longitudeRange  + 126.9684233228641;

    //         logDto.setLatitude(randomLatitude);
    //         logDto.setLongitude(randomLongitude);

    //         followSqlMapper.insertWriteTrackMarkInfo(logDto);
    //     }



    // }


    // // @Scheduled(cron = "0 0 5 * * *")
    // public void collect(){
    //     //...
    // }


}
