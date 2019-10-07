package kr.co.itcen.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
							HttpServletResponse response,
							Object handler)
							throws Exception {
		//1.handler 종류(DefaultServletHttpRequestHandler,HandlerMethod)
		if(handler instanceof HandlerMethod==false) {
			return true;
		}
		//2.casting
		HandlerMethod handlerMethod =(HandlerMethod)handler;
	
		//3.@Auth 받아오기
		Auth auth =handlerMethod.getMethodAnnotation(Auth.class);
		
		//4.@Auth가 없으면 class type에 있을 수 있으므로 ...
		if(auth==null) {
			//과제:class type에서 @Auth가 있는지를 확인해 봐야한다.
		}
		
		//5. @Auth가 없으면
		if(auth==null) {
			return true;
		}
		//6.@Auth가 class나 method에 붙어 있기 때문에 인증 여부를 체크한ㄷㅏ.
		HttpSession session = request.getSession();
		if(session==null || session.getAttribute("authUser")==null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
	    } 
		
		//8.Method의 @Auth의Role 가져오기
		String role = auth.value();
		
		//9.메소드의 @Auth의 Role이 "USER"인 경우.
		//인증만 되어있으면 모두 통과
		if(role == "USER") {
			return true;
		}
		//10..메소드의 @Auth의 Role이 "ADMIN"인 경우.
		//--과제 
		
		
		return true;
	}

}
