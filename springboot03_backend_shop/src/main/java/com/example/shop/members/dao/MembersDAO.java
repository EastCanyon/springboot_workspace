package com.example.shop.members.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.shop.members.dto.MembersDTO;

@Mapper
@Repository
public interface MembersDAO {
	
	public int insertMember(MembersDTO dto);
	public MembersDTO selectByEmail(String memberEmail);
	
	public void updateMember(MembersDTO dto);
	public void updateByPass(MembersDTO dto);

}
