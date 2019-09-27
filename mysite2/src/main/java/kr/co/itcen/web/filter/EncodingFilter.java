package kr.co.itcen.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;



public class EncodingFilter implements Filter {

	private String encoding;
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*request 처리*/
		request.setCharacterEncoding(encoding);
		
		chain.doFilter(request, response);
		
		/*response 처리*/
		
	}
	
	public void destroy() {

	}


	public void init(FilterConfig fConfig) throws ServletException {
		encoding =fConfig.getInitParameter("encoding");
		if(encoding==null) {
			encoding="utf-8";
		}

	}

}
