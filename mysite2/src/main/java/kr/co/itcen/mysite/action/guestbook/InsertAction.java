package kr.co.itcen.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.GuestbookDao;
import kr.co.itcen.mysite.vo.GuestbookVo;
import kr.co.itcen.web.mvc.Action;

public class InsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String contents = request.getParameter("contents");

		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setContents(contents);

		new GuestbookDao().insert(vo);
		response.sendRedirect(request.getContextPath() + "/guestbook");	

	}

}
