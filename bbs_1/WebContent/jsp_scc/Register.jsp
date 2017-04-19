<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="../css/Register.css" />
		<script src="./js_scc/jquery-3.2.1.min.js"></script>
		<script>
			function test() {
				var pd1 = document.getElementById("password");
				var pd2 = document.getElementById("password2");
				if(pd1.value != pd2.value) {
					alert("两次密码不一致！");
					return false;
				} else {
					return true;
				}
			}
		</script>
		<title>用户注册</title>
	</head>

	<body>
		<div id="register">
			<h1>注册成为VIP</h1>
			<form method="post" action="bbs_servlet" onsubmit="return test()">
				<input type="hidden" name="type" value="RegisterPage" />
				<input type="text" required="required" placeholder="用户名" name="username" style="ime-mode: disabled;" maxLength="15"></input>
				<input type="password" required="required" placeholder="密码" name="password" id="password" maxLength="15"></input> 
				<input type="password" required="required" placeholder="确认密码" name="password2" id="password2" maxLength="15"></input>
				<button id="sub" type="submit" class="but">注册</button>
			</form>
		</div>
	</body>

</html>