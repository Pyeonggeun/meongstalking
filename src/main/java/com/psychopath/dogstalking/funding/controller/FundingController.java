package com.psychopath.dogstalking.funding.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.funding.dto.FundingNewsDto;
import com.psychopath.dogstalking.funding.dto.FundingOrderDto;
import com.psychopath.dogstalking.funding.dto.FundingProductDto;
import com.psychopath.dogstalking.funding.dto.FundingReviewDto;
import com.psychopath.dogstalking.funding.dto.FundingWishlistDto;

import com.psychopath.dogstalking.funding.service.FundingServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/funding/*")
public class FundingController {

    @Autowired
    private FundingServiceImpl fundingService;

    @RequestMapping("listPage")
    public String listPage(Model model, HttpSession session,FundingWishlistDto paraWishDto){
        // List<Map<String,Object>> fundingList = fundingService.fundingListCall();
        // model.addAttribute("list", fundingList);

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
                imageFile2.transferTo(new File(rootPath+todayPath+fileName));
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
    public String newsRgstrPage(Model model, @RequestParam("id")int product_pk){
        System.out.println("product_pk : "+product_pk);
        model.addAttribute("product_pk", product_pk);
        return "funding/newsRgstrPage";
    }

    @RequestMapping("insertNewsProcesss")
    public String insertNewsProcess(@RequestParam("product_pk") int product_Pk, @RequestParam(name = "newsImage", required = false) MultipartFile newsImage ,FundingNewsDto paraFundingNewsDto){
        
        if(!(newsImage == null || newsImage.isEmpty())){ 

            String rootPath = "C:/uploadFiles/";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd/");
            String todayPath = sdf.format(new Date());

            File todayFolderForCreate = new File(rootPath + todayPath);

            if(!todayFolderForCreate.exists()){
                todayFolderForCreate.mkdirs();
            }

            String originaFileName = newsImage.getOriginalFilename();
            System.out.println(originaFileName);

            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            String fileName = uuid + "_" + currentTime;

            String ext = originaFileName.substring(originaFileName.lastIndexOf("."));
            fileName += ext;

            try{
                newsImage.transferTo(new File(rootPath+todayPath+fileName));
            }catch(Exception e){
                e.printStackTrace();
            }

            paraFundingNewsDto.setNews_image(todayPath + fileName);
            }

        fundingService.insertNews(paraFundingNewsDto);
        return "redirect:/funding/productControlPage?id=" + paraFundingNewsDto.getProduct_pk();
    }

    @RequestMapping("newsReadPage")
    public String newsReadPage(Model model,@RequestParam("id") int product_pk){
        model.addAttribute("newsList", fundingService.selectNewsById(product_pk));
        model.addAttribute("c_news", fundingService.countNewsByPk(product_pk));
        return "funding/newsReadPage";
    }

    @RequestMapping("cheeringPage")
    public String cheeringPage(Model model,@RequestParam("id")int product_pk){
        System.out.println("cheering page product_pk : "+product_pk);
        model.addAttribute("cheering", fundingService.selectCheering(product_pk));
        model.addAttribute("product_pk", product_pk);
        model.addAttribute("countByPk", fundingService.countCheeringByPk(product_pk));

        return"funding/cheeringPage";
    }

    // @RequestMapping("insertCheering")
    // public String insertCheering(HttpSession session, @RequestParam(value="cheeringImage", required = false) MultipartFile cheeringImage, @RequestParam("product_pk") String product_pk,FundingCheeringDto paraCheeringDto){

    //     int product_pkInt = Integer.parseInt(product_pk);

    //     if(cheeringImage == null || cheeringImage.isEmpty()){
    //     }
    //     else{
    //         String rootPath = "C:/uploadFiles/";

    //         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd/");
    //         String todayPath = sdf.format(new Date());

    //         File todayFolderForCreate = new File(rootPath + todayPath);

    //         if(!todayFolderForCreate.exists()){
    //             todayFolderForCreate.mkdirs();
    //         }

    //         String originaFileName = cheeringImage.getOriginalFilename();
    //         System.out.println(originaFileName);

    //         String uuid = UUID.randomUUID().toString();
    //         long currentTime = System.currentTimeMillis();
    //         String fileName = uuid + "_" + currentTime;

    //         String ext = originaFileName.substring(originaFileName.lastIndexOf("."));
    //         fileName += ext;

    //         try{
    //             cheeringImage.transferTo(new File(rootPath+todayPath+fileName));
    //         }catch(Exception e){
    //             e.printStackTrace();
    //         }

    //         paraCheeringDto.setCheering_image(todayPath + fileName);
    //     }
        
    //     UserDto userInfo = (UserDto)session.getAttribute("sessionUser");
    //     paraCheeringDto.setUser_pk(userInfo.getUser_pk());
    //     paraCheeringDto.setProduct_pk(product_pkInt);
    //     fundingService.insertCheering(paraCheeringDto);
    //     return "redirect:./cheeringPage?id="+ product_pk;
    // }

    @RequestMapping("productRgstrCompletePage")
    public String productRgstrCompletePage(){
        return "funding/productRgstrCompletePage";
    }

    //마이페이지 for 판매회원
    @RequestMapping("fundingSellerMyPage")
    public String fundingSellerMyPage(Model model, HttpSession session){
        UserDto sessionUser =(UserDto)session.getAttribute("sessionUser");
        List<FundingProductDto> fundingList = fundingService.fundingListCallForPk(sessionUser.getUser_pk());  
        //통계구하기
        Map<String,Object> statisticsMap = fundingService.selectStatistics(sessionUser.getUser_pk());
        //프로필 사진 가져오기
        String photo = fundingService.pickProfilePhoto(sessionUser.getUser_pk());
        model.addAttribute("listForPk", fundingList);
        model.addAttribute("stast",statisticsMap);
        model.addAttribute("photo",photo);

        return "funding/fundingSellerMyPage";
    }

    //마이 페이지 for 일반회원
    @RequestMapping("fundingUserMyPage")
    public String fundingUserMyPage(Model model, HttpSession session){
        UserDto sessionUser = (UserDto)session.getAttribute("sessionUser");
        int user_pk = sessionUser.getUser_pk();
        
        model.addAttribute("order",fundingService.selectOrderListByPk(user_pk));
        model.addAttribute("wish",fundingService.selectWishlistByPk(user_pk));
        model.addAttribute("count",fundingService.selectForMyPage(user_pk));
        model.addAttribute("photo",fundingService.pickProfilePhoto(user_pk));

        System.out.println("photo ="+fundingService.pickProfilePhoto(user_pk));

        return "funding/fundingUserMyPage";
    }

    //셀러 페이지에서 올린 상품 눌러서 들어온 페이지
    @RequestMapping("productControlPage")
    public String productControlPage(Model model, @RequestParam("id")int product_pk){
        Map<String,Object> productMap= fundingService.selectProductInfo(product_pk);
        System.out.println("product_pk"+product_pk);
        model.addAttribute("product",productMap);

        return "funding/productControlPage";
    }

    //리스트에서 들어간 제품 상세 페이지
    @RequestMapping("productDetailPage")
    public String productDetailPage(Model model, @RequestParam("id")int product_pk){
        Map<String,Object> productMap= fundingService.selectProductInfo(product_pk);
        List<Map<String,Object>> reviewList = fundingService.selectReview(product_pk);
        model.addAttribute("product",productMap);
        model.addAttribute("review", reviewList);
        return "funding/productDetailPage";
    }

    //상품 구매 페이지
    @RequestMapping("productPurchasePage")
    public String productPurchasePage(Model model, @RequestParam("id")int product_pk){

        Map<String,Object> productMap= fundingService.selectProductInfo(product_pk);
        System.out.println("product_pk"+product_pk);
        model.addAttribute("product",productMap);

        return"funding/productPurchasePage"; 
    }

    //상품구매 페이지에서 넘어온 정보를 세션에 담는 프로세스
    @RequestMapping("productPurchaseProcess")
    public String productPurchaseProcess(HttpSession session, HttpServletRequest request){
        
        int product_pk = Integer.parseInt(request.getParameter("product_pk"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        System.out.println("quantity :  " +quantity);
        System.out.println("product_pk" + product_pk);
        Map<String,Object> purchaseMap = new HashMap<>();
        purchaseMap.put("product_pk",product_pk );
        purchaseMap.put("quantity", quantity);

        session.setAttribute("purchaseMap", purchaseMap);

        return "redirect:/funding/productPaymentPage"; 
    }

    //상품 구매페이지에서 수량 체크 다 하고 넘어가서 결제하는 페이지 / 임시저장을 두자...?
    @RequestMapping("productPaymentPage")
    public String productPaymentPage(Model model,HttpSession session){
        Map<String,Object> purchaseMap = (Map<String,Object>)session.getAttribute("purchaseMap");
        int product_pk = (int)purchaseMap.get("product_pk"); 
        int quantity = (int)purchaseMap.get("quantity"); 
        System.out.println("product_pk : "+product_pk);

        Map<String,Object> productMap= fundingService.selectProductInfo(product_pk);
        model.addAttribute("product",productMap);

        return "funding/productPaymentPage";
    }

    //상품결제창에서 넘어온 정보를 오더테이블에 넣는 메소드
    @RequestMapping("productPaymentProcess")
    public String productPaymentProcess(HttpSession session,FundingOrderDto fundingOrderDto){
        UserDto sessionUser = (UserDto)session.getAttribute("sessionUser");
        int user_pk = sessionUser.getUser_pk();
        Map<String,Object> purchaseMap = (Map<String,Object>)session.getAttribute("purchaseMap");
        int product_pk = (int)purchaseMap.get("product_pk"); 
        int quantity = (int)purchaseMap.get("quantity");

        fundingOrderDto.setProduct_pk(product_pk);
        fundingOrderDto.setQuantity(quantity);
        fundingOrderDto.setUser_pk(user_pk);

        fundingService.insertOrder( fundingOrderDto);

        return "redirect:/funding/productPaymentCompletePage";
    }

    //상품결제 완료 알림창
    @RequestMapping("productPaymentCompletePage")
    public String productPaymentCompletePage(){
        return "funding/productPaymentCompletePage";
    }

    // //찜하기
    // @RequestMapping("insertWishlistProcess")
    // public String insertWishlistProcess(HttpSession session,@RequestParam("id")int product_pk,FundingWishlistDto paraWishDto){
    //     UserDto sessionUser = (UserDto)session.getAttribute("sessionUser");
    //     paraWishDto.setUser_pk(sessionUser.getUser_pk());
    //     paraWishDto.setProduct_pk(product_pk);
    //     fundingService.insertWish(paraWishDto);

    //     return "redirect:./listPage";
    // }

    // @RequestMapping("deleteWishlistProcess")
    // public String deleteWishlistProcess(HttpSession session,@RequestParam("id")int product_pk,FundingWishlistDto paraWishDto){
    //     UserDto sessionUser = (UserDto)session.getAttribute("sessionUser");
    //     paraWishDto.setUser_pk(sessionUser.getUser_pk());
    //     paraWishDto.setProduct_pk(product_pk);
    //     fundingService.deleteWish(paraWishDto);

    //     return "redirect:./listPage";
    // }

    //일반회원 마이페이지에서 구매 상품 관련 활동 눌렀을 때
    @RequestMapping("purchaseProductManagePage")
    public String purchaseProductManagePage(Model model,HttpSession session, @RequestParam("id")int order_pk){

        model.addAttribute("manage",fundingService.selectOrderInfo(order_pk));
        model.addAttribute("orderCount",fundingService.selectOrderCount(order_pk));
        System.out.println(fundingService.selectOrderCount(order_pk));
        return "funding/purchaseProductManagePage";
    }

    @RequestMapping("insertReviewProcess")
    public String insertReviewProcess(HttpSession session, 
        @RequestParam("product_pk") String product_pk,
        @RequestParam("order_pk") String order_pk,
        @RequestParam("reviewImage") MultipartFile reviewImage,FundingReviewDto paraReviewDto){

            int product_pkInt = Integer.parseInt(product_pk);
            int order_pkInt = Integer.parseInt(order_pk);
        
            if(reviewImage == null || reviewImage.isEmpty()){
            
            }else{
            String rootPath = "C:/uploadFiles/";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd/");
            String todayPath = sdf.format(new Date());

            File todayFolderForCreate = new File(rootPath + todayPath);

            if(!todayFolderForCreate.exists()){
                todayFolderForCreate.mkdirs();
            }

            String originaFileName = reviewImage.getOriginalFilename();
            System.out.println(originaFileName);

            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            String fileName = uuid + "_" + currentTime;

            String ext = originaFileName.substring(originaFileName.lastIndexOf("."));
            fileName += ext;

            try{
                reviewImage.transferTo(new File(rootPath+todayPath+fileName));
            }catch(Exception e){
                e.printStackTrace();
            }

            paraReviewDto.setReview_image(todayPath + fileName);
        }


        UserDto sessionUser = (UserDto)session.getAttribute("sessionUser");
        paraReviewDto.setUser_pk(sessionUser.getUser_pk());
        paraReviewDto.setOrder_pk(order_pkInt);
        paraReviewDto.setProduct_pk(product_pkInt);
        fundingService.insertReview(paraReviewDto);

        return "redirect:/funding/purchaseProductManagePage?id="+product_pkInt;
    }
}




