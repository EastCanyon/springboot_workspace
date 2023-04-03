package com.example.shop.board.service;

import java.util.List;

import com.example.shop.board.dto.BoardDTO;
import com.example.shop.board.dto.PageDTO;



public interface BoardService {
	public int countProcess(); 
	public List<BoardDTO> listProcess(PageDTO pv);
	public void insertProcess(BoardDTO dto);
	public BoardDTO contentProcess(int num);
	public BoardDTO updateSelectProcess(int num);
	public void updateProcess(BoardDTO dto, String urlpath);
	public void deleteProcess(int num, String urlpath);
	public String fileSelectprocess(int num);
	
}
