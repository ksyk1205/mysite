package kr.co.itcen.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//접근제어(ACL)////////////////////////////
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
		//////////////////////////////////////////////////////
		
		if(request.getParameter("password").equals("")) {
			new UserDao().update(authUser.getNo(),request.getParameter("name"),request.getParameter("gender"));
		}else {

		new UserDao().update(authUser.getNo(),request.getParameter("name"),request.getParameter("password"),request.getParameter("gender"));
		}
		
		UserVo vo = new UserVo();
		vo.setNo(authUser.getNo());
		vo.setName(request.getParameter("name"));

		
		session.setAttribute("authUser",vo);
		WebUtils.redirect(request, response, request.getContextPath());
	}

}
