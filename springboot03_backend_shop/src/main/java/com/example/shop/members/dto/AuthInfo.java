package com.example.shop.members.dto;

//로그인 성공 후 인증 상태 정보를 세션에 보관할 때 사용
public class AuthInfo {
	
	private String memberEmail;
	private String memberName;
	private String memberPass;
	
	public AuthInfo() {

	}

	public AuthInfo(String memberEmail, String memberPass) {
		this.memberEmail = memberEmail;
		this.memberPass = memberPass;
	}
	
	public AuthInfo(String memberEmail, String memberName, String memberPass) {
		this.memberEmail = memberEmail;
		this.memberName = memberName;
		this.memberPass = memberPass;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getMemberPass() {
		return memberPass;
	}
	
}
