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

public class ModifyAction implements Action {

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
		
		String no = request.getParameter("no");
		String title =request.getParameter("title");
		String contents = request.getParameter("contents");
		String reg_date = request.getParameter("reg_date");
		Long user_no = authUser.getNo();
		
		BoardVo vo1 = new BoardVo();
		
		vo1.setNo(Long.parseLong(no));
		vo1.setTitle(title);
		vo1.setContents(contents);
		vo1.setReg_date(reg_date);
		vo1.setUser_no(user_no);
		
		new BoardDao().modify(vo1);
		
		String page =request.getParameter("page");
		WebUtils.redirect(request, response,  request.getContextPath()+"/board?a=view&no="+no+"&page="+page);
	
	}

}
