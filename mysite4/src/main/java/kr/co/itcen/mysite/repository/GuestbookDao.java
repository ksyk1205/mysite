
package kr.co.itcen.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSession;

	//방명록에 가져올 리스트 
	public List<GuestbookVo> getList(){
		List<GuestbookVo> result = sqlSession.selectList("guestbook.getList");
		return result;
	}
	//방명록 글 삭제
	public Boolean delete(GuestbookVo vo) {
		int count =sqlSession.delete("guestbook.delete",vo);
		return count ==1;		
	}	
	//방명록 글 작성
	public Boolean insert(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insert",vo);		
		return count ==1;		
	}
	
	
	
}