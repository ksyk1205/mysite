package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.GuestbookDao;
import kr.co.itcen.mysite.vo.GuestbookVo;


@Service
public class GuestbookService {
	@Autowired
	private GuestbookDao guestbookDao;

	public void delete(GuestbookVo vo) {
		guestbookDao.delete(vo);		
	}
	public boolean delete(Long no,String password) {
		return guestbookDao.delete(no, password);
	}

	public void insert(GuestbookVo vo) {
		guestbookDao.insert(vo);
	}

	public List<GuestbookVo> getList() {
		return guestbookDao.getList();
	}
	
	public List<GuestbookVo> getList(Long page) {
		return guestbookDao.getList(page);
	}

}
