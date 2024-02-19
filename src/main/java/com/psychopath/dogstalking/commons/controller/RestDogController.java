package com.psychopath.dogstalking.commons.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.auction.dto.AuctionImageDto;
import com.psychopath.dogstalking.commons.service.DogServiceImpl;
import com.psychopath.dogstalking.dto.RestResponseDto;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/commons/dog/*")
public class RestDogController {

    @Autowired
    private DogServiceImpl dogService;


        @RequestMapping("previewImage")
        public RestResponseDto previewImage(@RequestParam("imageFile") MultipartFile imageFile){
            
            RestResponseDto restResponseDto = new RestResponseDto();
            
            // 파일 저장 로직
            if(imageFile != null) {
                // if(imageFile.isEmpty()) {
                //     continue;
                // }

                String rootPath = "C:/uploadFiles/";
                
                // 날짜별 폴더 생성.
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
                String todayPath = sdf.format(new Date());
                
                File todayFolderForCreate = new File(rootPath + todayPath);
                
                if(!todayFolderForCreate.exists()) {
                    todayFolderForCreate.mkdirs();
                }
                
                String originalFileName = imageFile.getOriginalFilename();

                //파일명 충돌 회피 - 랜덤, 시간 조합
                String uuid = UUID.randomUUID().toString();
                long currentTime = System.currentTimeMillis();
                String fileName = uuid + "_" + currentTime;
                
                // 확장자 추출
                String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                fileName += ext;
                
                try {
                    imageFile.transferTo(new File(rootPath + todayPath + fileName));					
                }catch(Exception e) {
                    e.printStackTrace();
                }
                
                restResponseDto.setData(todayPath + fileName);
            }



            restResponseDto.setResult("success");
            
            return restResponseDto;
        }      



}
