package com.example.shop.board.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.shop.board.dto.BoardDTO;
import com.example.shop.board.dto.PageDTO;
import com.example.shop.board.service.BoardService;
import com.example.shop.common.file.FileUpload;


@RestController
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Value("${spring.servlet.multipart.location}")
	private String filename;
	
	@Autowired
	private PageDTO pdto;
	
	private int currentPage;
	
	public BoardController() {
		
	}
	
	//http://localhost:8090/board/list/1
	
	@GetMapping("/board/list/{currentPage}") // 받는 것 //보내주는 것
	public Map<String, Object> listExecute(@PathVariable("currentPage") int currentPage, PageDTO pv) {
		Map<String, Object> map = new HashMap<>();
	    int totalRecord = boardService.countProcess();
	    if (totalRecord >= 1) {
	       //if(pv.getCurrentPage() == 0)
	    	  this.currentPage = currentPage;
//	       else
//	    	  this.currentPage = pv.getCurrentPage();

	       this.pdto = new PageDTO(this.currentPage, totalRecord);
	       
	       map.put("aList", boardService.listProcess(this.pdto));
	       map.put("pv", this.pdto);
	    }

	    return map;
	 }//end listExecute()
	 
	 //@RequestBody : json => 자바객체
	 //@ResponseBody : 자바객체 => json
	 //@PathVariable : /board/list/:num      => /board/list/{num}
	 //@RequsetParam : /board/list?num=value => /board/list?num=1 => /board/list
	 //multipart/form-data : @RequestBody 선언 없이 그냥 받음 BoardDTO dto
	
	 @PostMapping("/board/write")
	 public String writeProExecute(BoardDTO dto, PageDTO pv, HttpServletRequest req, HttpSession session
	    ) {
	    MultipartFile file = dto.getFilename();
	      
	    //System.out.println(dto.getMembersDTO().getMemberName());

	    // 파일 첨부가 있으면...
	    if (!file.isEmpty()) {
	       UUID random = FileUpload.saveCopyFile(file, req);
	       dto.setUpload(random + "_" + file.getOriginalFilename());
	    }

	      dto.setIp(req.getRemoteAddr());
	      
//	      AuthInfo authInfo = (AuthInfo)session.getAttribute("authInfo");
//	      dto.setMemberEmail(authInfo.getMemberEmail());

	      boardService.insertProcess(dto);

	      if (dto.getRef() != 0) {
	         //ratt.addAttribute("currentPage", pv.getCurrentPage());
	    	  return String.valueOf(pv.getCurrentPage());
	      }else {
	    	  return String.valueOf(1);
	      }

	      //return "redirect:/board/list.do";

	   }
	

}//end class
