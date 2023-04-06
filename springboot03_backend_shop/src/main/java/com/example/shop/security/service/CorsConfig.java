package com.example.shop.security.service;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;


import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;




@Configuration
@EnableWebSecurity


public class CorsConfig  {


    @Bean 
    public CorsFilter corsFilter() {
    	System.out.println("corsFilter==========================");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //내 서버가 응답할때 json을 자바스크립트에서 처리할 수 있게 할지를 설정한다.
        //When allowCredentials is true, allowedOrigins cannot contain the special value "*" since that cannot be set on the "Access-Control-Allow-Origin" response header. To allow credentials to a set of origins, list them explicitly or consider using "allowedOriginPatterns" instead.
        // config.addAllowedOrigin("http://localhost:3000"); //모든 요청 ip에 응답을 허용한다.
        config.addAllowedOriginPattern("*");  //포트번호 응답 다름 허용
        config.addAllowedHeader("*"); //모든 요청 header에 응답을 허용한다.
        config.addAllowedMethod("*"); //모든 post, get, put, delete, patch요청에 응답을 허용한다.
       
        config.addExposedHeader("Authorization");
       // config.addExposedHeader("refreshToken");
        source.registerCorsConfiguration("/**", config);    


        return new CorsFilter(source);
    }
}




