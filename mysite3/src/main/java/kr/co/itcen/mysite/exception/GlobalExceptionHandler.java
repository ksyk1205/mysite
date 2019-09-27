package kr.co.itcen.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Log Log = LogFactory.getLog( GlobalExceptionHandler.class );
	
	@ExceptionHandler( Exception.class )
	public void handlerException(
		HttpServletRequest request,
		HttpServletResponse response,
		Exception e) throws Exception {
		
		//1. 로깅
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		Log.error(errors.toString());

		//2. 안내 페이지
		request.setAttribute("uri", request.getRequestURI());
		request.setAttribute("exception", errors.toString());
		request
			.getRequestDispatcher("/WEB-INF/views/error/exception.jsp")
			.forward(request, response);
	}
}
