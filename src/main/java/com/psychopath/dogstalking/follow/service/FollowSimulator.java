package com.psychopath.dogstalking.follow.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.commons.mapper.UserSqlMapper;
import com.psychopath.dogstalking.commons.service.UserServiceImpl;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.follow.dto.LogDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;
import com.psychopath.dogstalking.follow.mapper.FollowSqlMapper;

@Service
public class FollowSimulator {

    @Autowired
    private UserSqlMapper userSqlMapper;

    @Autowired
    private FollowSqlMapper followSqlMapper;

    @Autowired
    private UserServiceImpl userService;

    public void register(){

        String [] namePieces = {"김","이","하","후","류","하","나","조","인","춘","김","태","호","배","진","우","임","나","연","심","진","용"};

        for(int i = 0; i < 500; i++){

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


    @Scheduled(cron = "0 0 0,3,6,9,12,15,18,21 * * *")
    public void marking(){
        // 매일 마킹을 한다...

        for(int x = 0; x < 500; x++){
            int userpk = (int)(Math.random()*400 + 4300);

            LogDto logDto = new LogDto();
            logDto.setUser_pk(userpk);
            logDto.setImage_link("2024/01/221f3d8671-527b-4367-866b-3efa12f0b596_1705906413210.webp");
            logDto.setContent(userpk + ": 시뮬레이터 입니다.");

            //위도는 37.58536437574824 이고, 경도는 126.9684233228641 입니다
            //클릭한 위치의 위도는 37.4716142577263 이고, 경도는 127.19203257577148 입니다
            double latitudeRange = 37.58536437574824 - 37.4716142577263;
            double longitudeRange = 127.19203257577148 - 126.9684233228641;

            double randomLatitude = Math.random()*latitudeRange  + 37.4716142577263;
            double randomLongitude = Math.random()*longitudeRange  + 126.9684233228641;

            logDto.setLatitude(randomLatitude);
            logDto.setLongitude(randomLongitude);

            followSqlMapper.insertWriteTrackMarkInfo(logDto);
        }



    }


    // @Scheduled(cron = "0 0 5 * * *")
    public void collect(){
        //...
    }


}
