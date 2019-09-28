package kr.co.itcen.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardVo;



@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;

	//검색을 하여 검색하는 해당하는 검색어에 있는 제목과 내용이 들어있는 리스트 들을 검색
	//처음 리스트 화면을 보여주기위한 getlist
	public List<BoardVo> getList(int page, String keyword) {
		Map<String,Object> map = new HashMap<String,Object>();
		keyword = "%"+keyword+"%";
		
		map.put("page", (page-1)*5);
		
		map.put("keyword", keyword);
		List<BoardVo> result = sqlSession.selectList("board.getList", map);
		return result;
	}

	//글의 총 개수를 가져오기 위한 count
	public int count(String keyword) {
		keyword = "%"+keyword+"%";
		int	t_count =sqlSession.selectOne("board.count",keyword);
		return t_count;

	}
	//게시글 제목을 눌렀을 때 내용을 보여주기 위한 view
	public BoardVo view(long no) {
		return sqlSession.selectOne("board.view",no);
	}
	
	//게시판에 글 쓰기를 위한 insert
	public Boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.write",vo);
		return count==1;		
	}
	
	
	
	// 답글 쓰기를 위한 newinsert
	public Boolean newinsert(BoardVo vo) {
		vo.setO_no(vo.getO_no()+1);
		vo.setDepth(vo.getDepth()+1);
		int count = sqlSession.insert("newinsert",vo);
		return count==1;
	}
	public Boolean updateinsert(BoardVo vo) {
		vo.setO_no(vo.getO_no()+1);
		vo.setDepth(vo.getDepth()+1);
		int count = sqlSession.update("updateinsert",vo);
		return count>0;
	}
	
	//view화면에 들어갈 때 hit가 증가하기위해서
	public Boolean hit(Long no) {
		int count = sqlSession.update("hit",no);
		return count==1;
	}

	// 게시글을 수정하기 위한 modify
	public boolean modify(BoardVo vo) {
		 int count = sqlSession.update("board.modify",vo);
		return count==1;		
	}
	//게시글을 삭제 하여 use_yn 값을 받아오기 위한 delete
	//use_yn이 1이면 글 삭제 
	public Boolean delete(long no) {
		int count = sqlSession.update("delete", no);
		return count ==1;
	}

}
