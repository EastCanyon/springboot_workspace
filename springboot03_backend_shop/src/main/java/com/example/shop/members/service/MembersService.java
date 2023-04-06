package com.example.shop.members.service;

import com.example.shop.members.dto.AuthInfo;
import com.example.shop.members.dto.ChangePwdCommand;
import com.example.shop.members.dto.MembersDTO;

public interface MembersService {
	
	public AuthInfo addMemberProcess(MembersDTO dto);
	public AuthInfo loginProcess(MembersDTO dto); 
	
	public MembersDTO updateMembersProcess(String memberEmail);
	public AuthInfo updateMemberProcess(MembersDTO dto);
	public void updatePassProcess(String memberEmail, ChangePwdCommand changePwd);

}
