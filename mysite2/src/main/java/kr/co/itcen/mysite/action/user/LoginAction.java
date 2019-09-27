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

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo userVo =new UserDao().get(email,password);
		if(userVo == null) {
			request.setAttribute("result", "fail");
			WebUtils.forward(request, response, "/WEB-INF/views/user/loginform.jsp");
			return;
		}
		
		
		//인증처리(Session 처리)
        HttpSession session = request.getSession(true); //없으면 만들어서!!
        session.setAttribute("authUser", userVo);
        
        WebUtils.redirect(request, response, request.getContextPath());
        

	}

}
