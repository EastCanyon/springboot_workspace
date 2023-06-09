package com.example.shop.members.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.members.dto.AuthInfo;
import com.example.shop.members.dto.MembersDTO;
import com.example.shop.members.service.MembersService;

import oracle.jdbc.proxy.annotation.Post;

//@CrossOrigin(origins={"http://localhost:3000"})
@CrossOrigin("*")

@RestController
public class MembersController {

	@Autowired
	private MembersService membersService;

	@Autowired
	private BCryptPasswordEncoder encodePassword;

	public MembersController() {
	}

	// http://localhost:8090/member/signup

	//회원가입 처리
	@PostMapping("/member/signup")
	public String addMember(@RequestBody MembersDTO membersDTO) {
		membersDTO.setMemberPass(encodePassword.encode(membersDTO.getMemberPass()));
		AuthInfo authInfo = membersService.addMemberProcess(membersDTO);
		return null;
	}//addMember
	
	//회원정보 가져오기
	@GetMapping("/member/editinfo/{memberEmail}")
	public MembersDTO getMember(@PathVariable("memberEmail") String memberEmail) {
		return membersService.updateMemberProcess(memberEmail);
	}
	
	@PostMapping("/member/update")
	public void updateMember(@RequestBody MembersDTO membersDTO) {
		membersDTO.setMemberPass(encodePassword.encode(membersDTO.getMemberPass()));
		membersService.updateMemberProcess(membersDTO);
	}

}//end class










