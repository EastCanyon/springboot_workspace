package com.example.demo.controller;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TodoDTO;
import com.example.demo.service.TodoService;

@CrossOrigin("*")
// @CrossOrigin({"http://localhost:3000"})

@RestController
//@Controller
public class TodoController {
	@Autowired
	private TodoService todoService;
	
	public TodoController() {
	}
	
	// http://localhost:8090/todo/all
	//@ResponseBody
	@GetMapping("/todo/all")
	public List<TodoDTO> getList() throws Exception{
		return todoService.search();
	}

	// http://localhost:8090/todo
	@PostMapping("/todo")
	public ResponseEntity<Object> postTodo(@RequestBody TodoDTO dto) throws Exception{
		int chk = todoService.insert(dto);
		
		HashMap<String, String> map = new HashMap<>();
		map.put("createDate", new Date().toString());
		map.put("message", "insert OK");
		map.put("cnt", String.valueOf(chk));
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		
		if(chk>=1) {
			// return new ResponseEntity<Object>(HttpStatus.OK);
			// return new ResponseEntity<Object>(map, HttpStatus.OK);
			// return new ResponseEntity<Object>(header, HttpStatus.OK);
			return new ResponseEntity<Object>(map, header, HttpStatus.OK);
			
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	// http://localhost:8090/todo/1/0
	@PutMapping("/todo/{id}/{completed}")
	public ResponseEntity<Object> putTodo(@PathVariable("id") int id,@PathVariable("completed")  int completed ) throws Exception{
		
		TodoDTO dto = new TodoDTO();
	      dto.setId(id);
	      dto.setCompleted(completed==0?1:0);
	      todoService.update(dto);
		
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	// http://localhost:8090/todo/1
	@DeleteMapping("/todo/{id}")
	public ResponseEntity<Object> deleteTodo(@PathVariable("id") int id) throws Exception {
		todoService.delete(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
}


















