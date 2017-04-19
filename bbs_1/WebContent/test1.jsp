<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*"%>
<%
	String username = "游客";
	Integer visitCount = new Integer(0);
	String visitCountKey = new String("visitCount");
	/* username=(String)request.getAttribute("usename"); */

	
	String name=session.getAttribute("username").toString();
	username=name;
/* 	if (session.isNew()) {//新登入的用户需要为session设置内容
		 username = request.getAttribute("usename").toString(); 
		session.setAttribute(visitCountKey, visitCount);
		session.setAttribute("usename", username);
	} else {//正在使用的用户不需要设置
		visitCount = (Integer) session.getAttribute(visitCountKey);
		visitCount += 1;
		username = session.getAttribute("usename").toString();
		session.setAttribute("usename", username);
	} */
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>
	<div style="position: absolute; top: 30%; left: 47%;">
		<h1>首页</h1>
		<a>用户名：</a>
		<%
			/* out.print(username); */
			out.print(username);
		%><br>
		<tr>
			<td>访问次数</td>
			<td>
				<%
					out.print(visitCount);
				%>
			</td>
		</tr>
	</div>

</body>
</html>