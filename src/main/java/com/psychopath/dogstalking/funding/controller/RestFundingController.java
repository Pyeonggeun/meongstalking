package com.psychopath.dogstalking.funding.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.funding.dto.FundingCheeringDto;
import com.psychopath.dogstalking.funding.dto.RestResponseFundingDto;
import com.psychopath.dogstalking.funding.service.FundingServiceImpl;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/funding/*")
public class RestFundingController {

    @Autowired
    private FundingServiceImpl fundingService;

    @RequestMapping("insertCheeringUsingAjax")
    public RestResponseFundingDto insertCheeringUsingAjax(HttpSession session,
    @RequestParam(value = "content", required = false) String content,
    @RequestParam(value = "product_pk", required = false) int product_Pk,
    @RequestParam(value = "cheering_Image", required = false) MultipartFile cheering_image,
    FundingCheeringDto paraCheeringDto){
        
        RestResponseFundingDto restResponseFundingDto = new RestResponseFundingDto();

        if(!(cheering_image == null || cheering_image.isEmpty())){ 

            String rootPath = "C:/uploadFiles/";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd/");
            String todayPath = sdf.format(new Date());

            File todayFolderForCreate = new File(rootPath + todayPath);

            if(!todayFolderForCreate.exists()){
                todayFolderForCreate.mkdirs();
            }

            String originaFileName = cheering_image.getOriginalFilename();
            System.out.println(originaFileName);

            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            String fileName = uuid + "_" + currentTime;

            String ext = originaFileName.substring(originaFileName.lastIndexOf("."));
            fileName += ext;

            try{
                cheering_image.transferTo(new File(rootPath+todayPath+fileName));
            }catch(Exception e){
                e.printStackTrace();
            }

            paraCheeringDto.setCheering_image(todayPath + fileName);
            }

        UserDto sessionUser = (UserDto)session.getAttribute("sessionUser");
        paraCheeringDto.setUser_pk(sessionUser.getUser_pk());

        fundingService.insertCheering(paraCheeringDto);

        restResponseFundingDto.setResult("success");
        return restResponseFundingDto;
    }

    @RequestMapping("callCheeringList")
    public RestResponseFundingDto callCheeringList(@RequestParam("product_pk")int product_pk){
        RestResponseFundingDto restResponseFundingDto = new RestResponseFundingDto();
        restResponseFundingDto.setResult("success");
        restResponseFundingDto.setData(fundingService.selectCheering(product_pk));

        return restResponseFundingDto;

    }

    @RequestMapping("toggleWish")
    public RestResponseFundingDto toggleWish(HttpSession session, FundingCheeringDto paraCheeringDto, @RequestParam("product_pk")int product_pk){
        RestResponseFundingDto restResponseFundingDto = new RestResponseFundingDto();

        UserDto sessionUser = (UserDto)session.getAttribute("sessionUser");
        paraCheeringDto.setUser_pk(sessionUser.getUser_pk());

        paraCheeringDto.setProduct_pk(product_pk);
        fundingService.insertCheering(paraCheeringDto);

        restResponseFundingDto.setResult("success");
        
        return restResponseFundingDto;
    }
}

//여기서 리퀘스트 하면 죄다 json으로 된다 포워딩 안 해도 된다. 모델 필요없고 값만 잘 리턴헤주자
//restapi 호출은 ajax 호출에서만 의미가 있다. / rest = ajax /링크로 넘어오면 안된다. 
//파라미터 하나만 날라온다 아마도 product_pk 파라미터 한번에 받아야 한다. 세션도 쓸거다
// 시작은 RestResponseFundingDto restResponseFundingDto = new RestResponseFundingDto(); 끝은 return restResponseFundingDto;
//파라미터는 FundingWishlistDto 한개인데 dto로 받는다. 어차피 생성해야 하기 때문. 
// 항상 구조 동일 세션에서 값 뽑아온다 / 오브젝트 다형성 문제 뭐였더라?