<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="../css/LoginPage.css" />
		<script src="./js_scc/jquery-3.2.1.min.js"></script>
		<title>用户登录</title>
	</head>
	<body>
		<div id="login">
			<h1>登录BBS</h1>
			<!-- 您输入的帐号或密码不正确，请重新输入。 -->
			<!-- 		<div>
			<a style="color: red; font-size: 10px; float: center; letter-spacing: 1px;">请输入账号密码。</a>
		</div> -->
			<form method="post" action="bbs_servlet">
				<input type="hidden" name="type" value="LoginPage"/>
				<input type="text" required="required" placeholder="用户名" name="username" style="ime-mode: disabled;" maxLength="15"></input>
				<input type="password" required="required" placeholder="密码" name="password" maxLength="15"></input>
				<button class="but" type="submit">登录</button>
				<a href="Register.jsp" style="float: right; padding: 10px; color: white; font-size: 14px;">没有账号?点击创建一个</a>
			</form>
		</div>
	</body>

</html>