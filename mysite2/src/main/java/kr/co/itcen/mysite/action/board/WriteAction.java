package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =request.getSession();
		if(session ==null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser ==null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		
		String title =request.getParameter("title");
		String contents = request.getParameter("contents");
		String parentNo = request.getParameter("parent_no");

		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUser_no(authUser.getNo());
		

		
		if (parentNo != null && parentNo.length() > 0) {
			vo.setNo(Long.parseLong(parentNo));
			
			new BoardDao().newinsert(vo);
		} else {
			new BoardDao().insert(vo);
		}
		
		String page=request.getParameter("page");
		WebUtils.redirect(request, response, request.getContextPath() + "/board?page="+page);	

	}

}
