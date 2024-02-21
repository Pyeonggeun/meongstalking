package com.psychopath.dogstalking.commons.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.commons.service.DogServiceImpl;
import com.psychopath.dogstalking.dto.DogDto;
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


        @RequestMapping("getDogList")
        public RestResponseDto getDogList(int userPk, HttpSession session) {
            RestResponseDto restResponseDto = new RestResponseDto();
            
            Map<String, Object> dogInfo = (Map<String, Object>)session.getAttribute("dogInfo");

            int dogPk = (int)dogInfo.get("dogPk");
            List<DogDto> dogList = dogService.getDogList(userPk);

            Map<String, Object> map = new HashMap<>();
            map.put("dogPk", dogPk);
            map.put("dogList", dogList);

            restResponseDto.setData(map);
            restResponseDto.setResult("success");
            
            return restResponseDto;
        }    

        @RequestMapping("getDogInfo")
        public RestResponseDto getDogInfo(int dogPk) {
            RestResponseDto restResponseDto = new RestResponseDto();
            
            restResponseDto.setData(dogService.getDogInfo(dogPk));
            restResponseDto.setResult("success");
            
            return restResponseDto;
        }    

        @RequestMapping("selectDog")
        public RestResponseDto selectDog(int dogPk, HttpSession session) {
            RestResponseDto restResponseDto = new RestResponseDto();

            Map<String, Object> map = new HashMap<>();
            boolean isDogExist = true;
            DogDto dogDto = dogService.getDogInfo(dogPk);

            map.put("dogImage", dogDto.getImage());
            map.put("dogName", dogDto.getName());
            map.put("dogPk", dogDto.getPk());
            map.put("isDogExist", isDogExist);

            session.setAttribute("dogInfo", map);
            
            restResponseDto.setData(null);
            restResponseDto.setResult("success");
            
            return restResponseDto;
        }    
}
