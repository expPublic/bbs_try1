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

	private ArrayList<String> sessionIDList=new ArrayList<>();
	private ArrayList<String> sessionNameList=new ArrayList<>();
	

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
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		// 获取来自LoginPage页面的账号密码进行数据库验证

		// HttpSession session=request.getSession();
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
		// System.out.println("注册成功");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// System.out.println(username+"\n"+password);
		boolean YorN = is_can_be_registered(username);
		PrintWriter out = response.getWriter();
		if (YorN) {// 可以注册
			int isSuccess = addUserData(username, password);
			if (isSuccess == 0) {// fail 出错概率小

				out.println("<script>alert('错误');window.history.go(-1)</script>");
			} else {// success
				System.out.println(isSuccess);
				response.sendRedirect("LoginPage.jsp");
			}
		} else {
			// 已经被注册了
			out.println("<script>alert('该用户名已被注册');window.history.go(-1)</script>");
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();

		boolean isSuccess = checkWetherExists(username, password);//检验数据库中是否存在该账号密码
		PrintWriter out = response.getWriter();
		if (!isSuccess) {
			out.println("<script>alert('账号密码错误');window.history.go(-1)</script>");// 登陆失败
		} else {
			session.setAttribute("username", username);
			session.setAttribute("ID", session.getId());
			addSession(session);//存在则不操作，不存在则添加进去。
			System.out.println(sessionNameList+"  "+sessionIDList);
//			boolean isLogined=checkWetherLogined(username,session.getId());//检验该账号是否在别处登录，别处登录的ID值跟此处一定不同
			boolean isLogined=true;
			if (!isLogined) {
				//该账户已经在别处登录
				out.println("<script>alert('该账号已在别处登录');window.history.go(-1)</script>");// 登陆失败
			}else {
				
				request.getRequestDispatcher("../index.jsp").forward(request, response);
			}
			
		}
	}

	private boolean checkWetherLogined(String username,String ID) {//该方法暂时不用
		// TODO Auto-generated method stub
		for (int i = 0; i < sessionNameList.size(); i++) {
			String sampleName = sessionNameList.get(i);
			String sampleID=sessionIDList.get(i);
			if (username==sampleName&&sampleID!=ID) {
				//登录名相同但是sessionID不同，则不允许登录返回false
				return false;
			}
			
		}
		return true;//别的情况返回true
	}
	public boolean checkWetherExists(String username, String password) {//验证登录，即检验账号密码是否与数据库中某字段对应
		String sql = "select * from users where username=? and password=?";
		Connection connection = DBUtil.open();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				System.out.println("登录成功！");
				return true;
			} else {
				System.out.println("登录失败！");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e2) {
				System.out.println("释放资源发生异常！");
			}

		}
		return false;

	}
	private void addSession(HttpSession session) {
		// TODO Auto-generated method stub
		if (sessionIDList.isEmpty()) {
			sessionIDList.add(session.getId());
			sessionNameList.add(session.getAttribute("username").toString());
			return;
		}
		for(int i=0;i<sessionIDList.size();i++){
			String sample=sessionIDList.get(i);
			if (session.getId()==sample) {
				//相同时候更新一下名字，不更新ID
				sessionNameList.remove(i);
				sessionIDList.remove(i);
				sessionNameList.add(session.getAttribute("username").toString());
				sessionIDList.add(session.getId());
				return;
			}
		}
		//不相同则添加
		sessionIDList.add(session.getId());
		sessionNameList.add(session.getAttribute("username").toString());
	}

	public boolean is_can_be_registered(String username) {//检验该用户名是否可以注册
		String sql = "select * from users where username=?";
		Connection connection = DBUtil.open();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			rs = preparedStatement.executeQuery();
			rs.last();
			int length = rs.getRow();
			rs.first();
			if (length > 0) {
				// 注册名重复
				preparedStatement.close();
				connection.close();
				return false;
			} else {
				// 该用户名可以注册
				preparedStatement.close();
				connection.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	

	public int addUserData(String username, String password) {// 注册用户到数据库
		String sql = "insert into users(username,password)values(?,?)";
		int i = 0;// 0则失败，非0则成功
		Connection connection = DBUtil.open();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			i = preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;

	}
}
