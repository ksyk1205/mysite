package kr.co.itcen.mysite.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import kr.co.itcen.mysite.web.MVCConfig;

@Configuration
@EnableAspectJAutoProxy //<aop:aspectj-autoproxy />
@ComponentScan({"kr.co.itcen.mysite.controller"})
@Import({MVCConfig.class,})
public class WebConfig {

}
