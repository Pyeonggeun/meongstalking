package com.psychopath.dogstalking.club.controller;

import org.apache.ibatis.javassist.compiler.ast.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.club.dto.ClubImgBoardDto;
import com.psychopath.dogstalking.club.dto.CommentDto;
import com.psychopath.dogstalking.club.dto.ImgCommentDto;
import com.psychopath.dogstalking.club.service.ClubServiceImpl;
import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.dto.UserDto;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/club/*")
public class ClubRestController {

	@Autowired
	private ClubServiceImpl clubService;

	@RequestMapping("loadSignupModal")
	public RestResponseDto companyListModal(int pk) {

		RestResponseDto response = new RestResponseDto();
		response.setResult("success");

		response.setData(clubService.getloadSignupModal(pk));

		return response;
	}

	// freeboard
	@RequestMapping("writeComment")
	public RestResponseDto writeComment(HttpSession session, CommentDto params) {
		RestResponseDto restResponseDto = new RestResponseDto();

		UserDto userDto = (UserDto) session.getAttribute("sessionUser");
		params.setUser_pk(userDto.getUser_pk());

		clubService.writeComment(params);

		restResponseDto.setResult("success");
		return restResponseDto;
	}

	@RequestMapping("getMyId")
	public RestResponseDto getMyId(HttpSession session) {
		RestResponseDto restResponseDto = new RestResponseDto();
		System.out.println("getMyId 진입");
		UserDto sessionUser = (UserDto) session.getAttribute("sessionUser");

		restResponseDto.setResult("success");

		if (sessionUser != null) {
			restResponseDto.setData(sessionUser.getUser_pk());
		}

		return restResponseDto;
	}

	@RequestMapping("getCommentList")
	public RestResponseDto getCommentList(@RequestParam("clubfreeboard_pk") int articleId) {
		RestResponseDto restResponseDto = new RestResponseDto();

		restResponseDto.setData(clubService.getCommentList(articleId));

		restResponseDto.setResult("success");
		return restResponseDto;
	}

	@PostMapping("/uploadImage")
	public ResponseEntity<String> handleImageUpload(@RequestPart("imageFile") MultipartFile imageFile,
			ClubImgBoardDto clubImgBoardDto) {
		if (imageFile != null && !imageFile.isEmpty()) {
			String originalFilename = imageFile.getOriginalFilename();
			clubImgBoardDto.setImg(originalFilename);
			return new ResponseEntity<>(originalFilename, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("이미지 업로드 실패", HttpStatus.BAD_REQUEST);
		}
	}

	// imgfreeboard
	@RequestMapping("writeImgComment")
	public RestResponseDto writeImgComment(HttpSession session, ImgCommentDto params) {
		RestResponseDto restResponseDto = new RestResponseDto();

		UserDto userDto = (UserDto) session.getAttribute("sessionUser");
		params.setClub_user_pk(userDto.getUser_pk());

		clubService.writeImgComment(params);

		restResponseDto.setResult("success");
		return restResponseDto;
	}

	@RequestMapping("getImgCommentList")
	public RestResponseDto getImgCommentList(@RequestParam("clubimgboard_pk") int articleId) {
		RestResponseDto restResponseDto = new RestResponseDto();

		restResponseDto.setData(clubService.getImgCommentList(articleId));
		restResponseDto.setResult("success");
		return restResponseDto;
	}

	@RequestMapping("updateImgComment")
	public RestResponseDto updateImgComment(ImgCommentDto params) {
		RestResponseDto restResponseDto = new RestResponseDto();

		clubService.updateImgComment(params);

		restResponseDto.setResult("success");
		return restResponseDto;
	}

	@RequestMapping("deleteImgComment")
	public RestResponseDto deleteImgComment(int comment_id) {
		RestResponseDto restResponseDto = new RestResponseDto();

		clubService.deleteImgComment(comment_id);

		restResponseDto.setResult("success");
		return restResponseDto;
	}

	// @RequestMapping("performSearch")
	@RequestMapping(value = "/performSearch", method = RequestMethod.GET)
	public RestResponseDto performSearch(@RequestParam("searchQuery") String searchQuery) {

		//System.out.println("searchQuery : " + searchQuery);

		RestResponseDto restResponseDto = new RestResponseDto();

		clubService.searchMember(searchQuery);
		List<Map<String, Object>> searchResults = clubService.searchMember(searchQuery);
		//System.out.println("Search results from server: " + searchResults);
		restResponseDto.setResult("success");
		restResponseDto.setData(searchResults);
		return restResponseDto;
	}

}
