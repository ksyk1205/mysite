<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
$(function(){
	$("#input-email").change(function(){
		$("#btn-check-email").show();
		$("#img-checked").hide();
	});
	$("#btn-check-email").click(function(){
		//http://localhost:8088/mysite3/api/user/checkemail?email=
		var email =$("#input-email").val();
		if(email==""){
			return;
		}
		//ajax 통신
		$.ajax({
			url:"/mysite3/api/user/checkemail?email="+email,
			type:"get",
			dataType:"json",
			data:"",
			success:function(response){
				if(response.result == "fail"){
					console.error(response.message);
					retrun;
				}
				if(response.data==true){
					alert("이미 존재하는 메일입니다.");
					$("#input-email").val("");
					$("#input-email").focus();
					return;
				}
				$("#btn-check-email").hide();
				$("#img_checked").show();
			},
			error:function(xhr,error){
				console.error("error:"+error);
			}
		});
	});
});

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				
				<form id="join-form" name="joinForm" method="post" action="${pageContext.servletContext.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="${userVo.name }">

					<label class="block-label" for="email">이메일</label>
					<input id="input-email" name="email" type="text" value="">
					<input id="btn-check-email" type="button" value="중복 확인">
					<img id="img_checked" style='width:20px; display:none' src='${pageContext.servletContext.contextPath }/assets/images/check.png'/>
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
