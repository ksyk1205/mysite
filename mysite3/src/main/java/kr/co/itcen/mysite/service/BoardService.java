package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	
	public List<BoardVo> getList(int page, String keyword) {
		return boardDao.getList(page,keyword);
	}
	
	public Boolean count(String keyword) {
		return boardDao.count(keyword);
		
	}

}
