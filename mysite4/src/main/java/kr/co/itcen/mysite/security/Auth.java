package kr.co.itcen.mysite.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD}) //어디다가 붙일 수 있는지 지정
@Retention(RetentionPolicy.RUNTIME) 
public @interface Auth {
//	public enum Role{USER,ADMIN}; Enum으로 바깥에서도 만들 수 있음
	
//	public Role role() default Role.USER;
	public String value() default "USER";
	
//	public int test() default 1;
	
}
