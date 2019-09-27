package kr.co.itcen.mysite.action.user;

import kr.co.itcen.mysite.action.main.MainAction;
import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

public class UserActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		//actionName "JoinForm"
		//actionName +"Action" ->  객체를 생성하는 코드 
		//String으로 된  클래스 이름으로 동적 객체 생성
		
		Action action = null;
		
		
		if("joinform".equals(actionName)) {
			action = new JoinFormAction();
		
		}else if("joinsuccess".equals(actionName)){
			action = new JoinSuccessAction();
		
		}else if("join".equals(actionName)){
			action = new JoinAction();
			
			
		}else if("loginform".equals(actionName)){
				action = new LoginFormAction();
			
		}else if("login".equals(actionName)){
			action = new LoginAction();

		}else if("logout".equals(actionName)){
			action = new LogoutAction();
			
		}else if("updateform".equals(actionName)){
			action = new UpdateFormAction();
			
		}else if("update".equals(actionName)){
			action = new UpdateAction();
			
		}else {
			action =new MainAction();
		}
		return action;
	}

}
