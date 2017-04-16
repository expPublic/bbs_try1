<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/LoginPage.css" />
<title>用户登录</title>
</head>
<body>
	<div id="login" action="/bbs_1/src/package_scc/bbs_servlet.java"">
		<h1>登录BBS</h1>
		<form method="post">
			<input type="text" required="required" placeholder="用户名" name="u"></input>
			<input type="password" required="required" placeholder="密码" name="p"></input>
			<button class="but" type="submit">登录</button>
			<a href="Register.jsp" style="float: right;padding: 10px;color: white; ">没有账号?点击创建一个</a>
		</form>
	</div>
</body>
</html>