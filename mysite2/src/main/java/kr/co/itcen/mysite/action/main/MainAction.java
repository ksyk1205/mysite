package kr.co.itcen.mysite.action.main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class MainAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Cookie Test
		int count = 0;
		
		//Cookie 읽기
		Cookie[] cookies =request.getCookies();
		if(cookies!=null&&cookies.length>0) {
			for(Cookie cookie:cookies) {
				if("visitCount".contentEquals(cookie.getName())) {
					count = Integer.parseInt(cookie.getValue());
				}
			}
		}
		
		//Cookie 쓰기
		 count++;
		 Cookie cookie = new Cookie("visitCount",String.valueOf(count));
		 cookie.setMaxAge(60*60*24);//1day
		 cookie.setPath(request.getContextPath());
		 response.addCookie(cookie);
		
		WebUtils.forward(request, response, "/WEB-INF/views/main/index.jsp");

	}

}
