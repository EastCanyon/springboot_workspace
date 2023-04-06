package com.example.shop.security.jwt;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.shop.members.dto.MembersDTO;
import com.example.shop.members.dto.User;
import com.example.shop.security.service.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

//Authentication(인증)
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authManager;

	public JwtAuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
	}

	//http://localhost:8090/login 요청을 하면 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter => login 요청 처리를 시작함");

		try {
			ObjectMapper om = new ObjectMapper();
			MembersDTO user = om.readValue(request.getInputStream(), MembersDTO.class);
			System.out.printf("memberEmail : %s, memberPass : %s\n", user.getMemberEmail(), user.getMemberPass());

			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(user.getMemberEmail(), user.getMemberPass());

			System.out.println(authManager);
			Authentication authentication = authManager.authenticate(authenticationToken);
			System.out.println(authentication.getPrincipal());

			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			System.out.printf("로그인 완료됨(인증) : %s %s\n", principalDetails.getUsername(), principalDetails.getPassword());

			return authentication;
		}catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}


	//attemptAuthentication() 실행 후 인증이 정상적으로 완료되면 실행된다.
	//여기에서 JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response해준다.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication 실행됨");

		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();

		String jwtToken = JWT.create()
				.withSubject("mycors")
				.withExpiresAt(new Date(System.currentTimeMillis() + (60 * 1000 *60*1L)))//만료시간 3분
				.withClaim("memberName", principalDetails.getMembersDTO().getMemberName())//회원 이름
				.withClaim("authRole", principalDetails.getMembersDTO().getAuthRole())//회원 권한
				.withClaim("memberEmail", principalDetails.getMembersDTO().getMemberEmail())//회원 메일
				.sign(Algorithm.HMAC512("mySecurityCos")); //signature를 생성하기 위한 security

		System.out.println("jwtToken:" + jwtToken);

		//response 응답헤더에 jwtToken 추가
		response.addHeader("Authorization", "Bearer " + jwtToken);

		final Map<String, Object> body = new HashMap<String, Object>();
		body.put("memberName", principalDetails.getMembersDTO().getMemberName());
		body.put("memberEmail", principalDetails.getMembersDTO().getMemberEmail());
		body.put("authRole", principalDetails.getMembersDTO().getAuthRole());

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("unsuccess");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("code", HttpStatus.UNAUTHORIZED.value());
		body.put("error", failed.getMessage());

		new ObjectMapper().writeValue(response.getOutputStream(), body);



		super.unsuccessfulAuthentication(request, response, failed);
	}



}//end class
