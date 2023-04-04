package com.example.shop.board.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.board.dao.BoardDAO;
import com.example.shop.board.dto.BoardDTO;
import com.example.shop.board.dto.PageDTO;



@Service
public class BoardServiceImp implements BoardService {
	
	@Autowired
	private BoardDAO boardDao;
	
	public BoardServiceImp() {

	}

	@Override
	public int countProcess() {
		return boardDao.count();
	}

	@Override
	public List<BoardDTO> listProcess(PageDTO pv) {
		return boardDao.list(pv);
	}

	@Override
	public void insertProcess(BoardDTO dto) {
		//답변글이면
		if(dto.getRef() != 0) {
			boardDao.reStepCount(dto);
			dto.setRe_step(dto.getRe_step() + 1);
			dto.setRe_level(dto.getRe_level() + 1);
		}
		
		boardDao.save(dto);
	}

	@Override
	public BoardDTO contentProcess(int num) {
		boardDao.readCount(num);
		return boardDao.content(num);
	}

//	@Override
//	public BoardDTO updateSelectProcess(int num) {
//		return boardDao.content(num);
//	}

	@Override
	public void updateProcess(BoardDTO dto, String urlpath) {
		String filename = dto.getUpload();
		
		//수정할 첨부 파일이 있으면
		if(filename != null) {
			
			String path = boardDao.getFile(dto.getNum());
			//기존 첨부 파일이 있으면
			if(path != null) {
				File file = new File(urlpath, path);
				file.delete();
			}
		}
		boardDao.update(dto);
	}

	@Override
	public void deleteProcess(int num, String urlpath) {
		String path = boardDao.getFile(num);
		if(path!=null) {
			File file = new File(urlpath, path);
			file.delete();
		}
		boardDao.delete(num);
	}

	@Override
	public String fileSelectprocess(int num) {
		// TODO Auto-generated method stub
		return null;
	}
	
}//end class
