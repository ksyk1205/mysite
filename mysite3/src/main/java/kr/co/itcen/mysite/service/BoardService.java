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
	
	public List<BoardVo> getListkeyword(int page, String keyword) {
		return boardDao.getList(page,keyword);
	}
	public int count(String keyword) {
		return boardDao.count(keyword);
		
	}
	public int countkeyword(String keyword) {
		return boardDao.count(keyword);
	}
	public Boolean write(BoardVo vo) {
		return boardDao.insert(vo);
	}
	public Boolean newinsert(BoardVo vo) {
		return boardDao.newinsert(vo);
	}
	public Boolean updateinsert(BoardVo vo) {
		return boardDao.updateinsert(vo);
	}
	public BoardVo view(long no) {
		return boardDao.view(no);
	}

	public Boolean modify(BoardVo vo) {
		// TODO Auto-generated method stub
		return boardDao.modify(vo);
	}

	
}
