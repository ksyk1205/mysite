package kr.co.itcen.mysite.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import kr.co.itcen.config.web.FileuploadConfig;
import kr.co.itcen.config.web.MVCConfig;
import kr.co.itcen.config.web.MessageConfig;
import kr.co.itcen.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy //<aop:aspectj-autoproxy />
@ComponentScan({"kr.co.itcen.mysite.controller"})
@Import({MVCConfig.class,SecurityConfig.class,MessageConfig.class, FileuploadConfig.class})
public class WebConfig {

}
