package com.psychopath.dogstalking.funding.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.checkerframework.common.util.report.qual.ReportWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.funding.dto.FundingCheeringDto;
import com.psychopath.dogstalking.funding.dto.FundingNewsDto;
import com.psychopath.dogstalking.funding.dto.FundingProductDto;
import com.psychopath.dogstalking.funding.service.FundingServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/funding/*")
public class FundingController {

    @Autowired
    private FundingServiceImpl fundingService;

    @RequestMapping("listPage")
    public String listPage(Model model){
        List<Map<String,Object>> fundingList = fundingService.fundingListCall();            
        model.addAttribute("list", fundingList);

        return "funding/listPage";
    }

    @RequestMapping("prepareRgstrPage")
    public String prepareRgstrPage(){
        return "funding/prepareRgstrPage";
    }


    //rgstr = register
    @RequestMapping("productRgstrPage")
    public String productRgstrPage(){

        return "funding/productRgstrPage";
    }

    @RequestMapping("insertProductInfoProcess")
    public String insertProductInfoProcess(HttpSession session,FundingProductDto productDto, @RequestParam("imageFile1") MultipartFile imageFile1, @RequestParam("imageFile2")MultipartFile imageFile2){
        
        //이미지 넣기
        System.out.println("ㅁㅁㅁㅁㅁㅁㅁㅁㅁ");
        if(imageFile1 != null){
                String rootPath = "C:/uploadFiles/";

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd/");
                String todayPath = sdf.format(new Date());

                File todayFolderForCreate = new File(rootPath + todayPath);

                if(!todayFolderForCreate.exists()){
                    todayFolderForCreate.mkdirs();
                }

                String originaFileName = imageFile1.getOriginalFilename();
                System.out.println(originaFileName);

                String uuid = UUID.randomUUID().toString();
                long currentTime = System.currentTimeMillis();
                String fileName = uuid + "_" + currentTime;

                String ext = originaFileName.substring(originaFileName.lastIndexOf("."));
                fileName += ext;

                try{
                    imageFile1.transferTo(new File(rootPath+todayPath+fileName));
                }catch(Exception e){
                    e.printStackTrace();
                }

                productDto.setTitle_image(todayPath + fileName);
                }

        if(imageFile2 != null){
            String rootPath = "C:/uploadFiles/";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd/");
            String todayPath = sdf.format(new Date());

            File todayFolderForCreate = new File(rootPath + todayPath);

            if(!todayFolderForCreate.exists()){
                todayFolderForCreate.mkdirs();
            }

            String originaFileName = imageFile2.getOriginalFilename();
            System.out.println(originaFileName);

            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            String fileName = uuid + "_" + currentTime;

            String ext = originaFileName.substring(originaFileName.lastIndexOf("."));
            fileName += ext;

            try{
                imageFile1.transferTo(new File(rootPath+todayPath+fileName));
            }catch(Exception e){
                e.printStackTrace();
            }

            productDto.setExplain_image(todayPath + fileName);
            }        
    


        //세션 정보 뽑아 넣기 
        UserDto sessionUser  = (UserDto)session.getAttribute("sessionUser");
        productDto.setUser_pk(sessionUser.getUser_pk());
        fundingService.insertProductInfo(productDto);
        System.out.println("productDto userpk"+productDto.getUser_pk());
        System.out.println("productDto title image"+productDto.getTitle_image());        
        

        return "funding/productRgstrCompletePage";
    }


    @RequestMapping("newsRgstrPage")
    public String newsRgstrPage(){
        return "funding/newsRgstrPage";
    }

    @RequestMapping("insertNewsProcesss")
    public String insertNewsProcess(FundingNewsDto paraFundingNewsDto){
        fundingService.insertNews(paraFundingNewsDto);
        return "redirect:funding/newsRestrPage";
    }

    @RequestMapping("newsReadPage")
    public String newsReadPage(Model model, int product_pk){
        model.addAttribute("newsList", fundingService.selectNewsById(product_pk));
        return "funding/newsReadPage";
    }

    @RequestMapping("cheeringPage")
    public String cheeringPage(){
        return"funding/cheeringPage";
    }

    @RequestMapping("insertCheeringText")
    public String insertCheeringText(HttpSession session, FundingCheeringDto paraCheeringDto){
        UserDto userInfo = (UserDto)session.getAttribute("sessionUser");
        paraCheeringDto.setUser_pk(userInfo.getUser_pk());
        fundingService.insertCheeringText(paraCheeringDto);
        return "return: funding/cheeringPage";
    }

    @RequestMapping("productRgstrCompletePage")
    public String productRgstrCompletePage(){
        return "funding/productRgstrCompletePage";
    }


    @RequestMapping("fundingSellerMyPage")
    public String fundingSellerMyPage(){
        return "funding/fundingSellerMyPage";
    }

    
    @RequestMapping("fundingUserMyPage")
    public String fundingUserMyPage(){
        return "funding/fundingUserMyPage";
    }



    // @RequestMapping("productDetailPage")
    // public String productDetailPage(){
        
    //     return "#";
    // }

    // @RequestMapping("productPurchasePage")
    // public String productPurchasePage(){

    //     return"#"; 
    // }

    // @RequestMapping("productPurchaseProcess")
    // public String productPurchaseProcess(){

    //     return"#"; 
    // }


}
