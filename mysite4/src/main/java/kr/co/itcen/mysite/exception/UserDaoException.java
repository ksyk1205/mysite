package kr.co.itcen.mysite.exception;

public class UserDaoException extends RuntimeException {  //runtimeexception을 해주면 userservice에서  try,catch를 안해줘도된다

	public UserDaoException() {
		super("UserDaoException Occurs");
	}

	public UserDaoException(String message) {
		super(message);
	}
	
}
