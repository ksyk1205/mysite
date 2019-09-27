package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int o_page=1;
		//현재 페이지 값을 가져온다 o_page
		if(request.getParameter("page")!=null) {		
			String page = request.getParameter("page");
			//페이지의 값이 없을때 1페이지를 보여주기
			if (page == null || page.length() == 0)
				page = "1";
			o_page=Integer.parseInt(page);
		}
		//검색하기위한 keyword
		String keyword = request.getParameter("keyword");
		if(keyword == null) {
			keyword = "";
		}
		//1~5 까지 0 
		//6~10까지 5
		//11~15까지 10  pagenumber
		int pagenumber=((o_page-1)/5)*5;
		
		List<BoardVo> vo = new BoardDao().getList((o_page-1)*5,keyword);
		//게시판의 글 총 개수를 구하는 total_count
		int total_count = new BoardDao().Count(keyword);
		
		//한페이지에 5개씩 글이 뜨도록하기위해서 index 만들기
		int index =total_count-((o_page-1)*5);
		
		request.setAttribute("keyword", keyword);
		request.setAttribute("o_page", o_page);
		request.setAttribute("pagenumber", pagenumber);
		request.setAttribute("total_count", total_count);
		request.setAttribute("index", index);
		request.setAttribute("vo", vo );		
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");

	}

}
