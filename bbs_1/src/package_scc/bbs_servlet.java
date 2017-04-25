package package_scc;

import java.awt.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import package_public.UserBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

/**
 * Servlet implementation class bbs_servlet
 */
@WebServlet("/jsp_scc/bbs_servlet")
public class bbs_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ArrayList<String> sessionIDList = new ArrayList<>();
	private ArrayList<String> sessionNameList = new ArrayList<>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public bbs_servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = "ff";
		type = request.getParameter("type");
		switch (type) {
		case "LoginPage":
			login(request, response);
			break;
		case "RegisterPage":
			register(request, response);
			break;
		default:
			PrintWriter out = response.getWriter();
			out.println("waiting for updating!");
			break;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean YorN = DBUtil.is_can_be_registered(username);
		PrintWriter out = response.getWriter();
		if (YorN) {// 可以注册
			UserBean user = new UserBean();
			user.setUsername(username);
			user.setPassword(password);
			int isSuccess = DBUtil.insert(user);
			if (isSuccess == 0) {// fail 出错概率小
				out.println("<script>alert('错误');window.history.go(-1)</script>");// 弹窗提示
			} else {// success
				System.out.println(isSuccess);
				response.sendRedirect("LoginPage.jsp");// 跳转至登录界面
			}
		} else {
			// 已经被注册了
			out.println("<script>alert('该用户名已被注册');window.history.go(-1)</script>");
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		UserBean user = new UserBean();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		HttpSession session = request.getSession();
		boolean isSuccess = DBUtil.query_forLogin(user);// 检验数据库中是否存在该账号密码
		PrintWriter out = response.getWriter();
		if (!isSuccess) {
			out.println("<script>alert('账号密码错误');window.history.go(-1)</script>");// 登陆失败
		} else {
			session.setAttribute("username", user.getUsername());
			session.setAttribute("ID", session.getId());
			addSession(session);// 存在则不操作，不存在则添加进去。
			System.out.println(sessionNameList + "  " + sessionIDList);
			// boolean
			// isLogined=checkWetherLogined(username,session.getId());//检验该账号是否在别处登录，别处登录的ID值跟此处一定不同
			boolean isLogined = true;
			if (!isLogined) {
				// 该账户已经在别处登录
				out.println("<script>alert('该账号已在别处登录');window.history.go(-1)</script>");// 登陆失败
			} else {

				request.getRequestDispatcher("../index.jsp").forward(request, response);// 此方法不会在地址栏显示出index.jsp信息
			}
		}
	}

	private void addSession(HttpSession session) {
		// TODO Auto-generated method stub
		if (sessionIDList.isEmpty()) {
			sessionIDList.add(session.getId());
			sessionNameList.add(session.getAttribute("username").toString());
			return;
		}
		for (int i = 0; i < sessionIDList.size(); i++) {
			String sample = sessionIDList.get(i);
			if (session.getId() == sample) {
				// 相同时候更新一下名字，不更新ID
				sessionNameList.remove(i);
				sessionIDList.remove(i);
				sessionNameList.add(session.getAttribute("username").toString());
				sessionIDList.add(session.getId());
				return;
			}
		}
		// 不相同则添加
		sessionIDList.add(session.getId());
		sessionNameList.add(session.getAttribute("username").toString());
	}
}
