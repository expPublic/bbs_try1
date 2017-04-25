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
		if (YorN) {// ����ע��
			UserBean user = new UserBean();
			user.setUsername(username);
			user.setPassword(password);
			int isSuccess = DBUtil.insert(user);
			if (isSuccess == 0) {// fail �������С
				out.println("<script>alert('����');window.history.go(-1)</script>");// ������ʾ
			} else {// success
				System.out.println(isSuccess);
				response.sendRedirect("LoginPage.jsp");// ��ת����¼����
			}
		} else {
			// �Ѿ���ע����
			out.println("<script>alert('���û����ѱ�ע��');window.history.go(-1)</script>");
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		UserBean user = new UserBean();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		HttpSession session = request.getSession();
		boolean isSuccess = DBUtil.query_forLogin(user);// �������ݿ����Ƿ���ڸ��˺�����
		PrintWriter out = response.getWriter();
		if (!isSuccess) {
			out.println("<script>alert('�˺��������');window.history.go(-1)</script>");// ��½ʧ��
		} else {
			session.setAttribute("username", user.getUsername());
			session.setAttribute("ID", session.getId());
			addSession(session);// �����򲻲���������������ӽ�ȥ��
			System.out.println(sessionNameList + "  " + sessionIDList);
			// boolean
			// isLogined=checkWetherLogined(username,session.getId());//������˺��Ƿ��ڱ𴦵�¼���𴦵�¼��IDֵ���˴�һ����ͬ
			boolean isLogined = true;
			if (!isLogined) {
				// ���˻��Ѿ��ڱ𴦵�¼
				out.println("<script>alert('���˺����ڱ𴦵�¼');window.history.go(-1)</script>");// ��½ʧ��
			} else {

				request.getRequestDispatcher("../index.jsp").forward(request, response);// �˷��������ڵ�ַ����ʾ��index.jsp��Ϣ
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
				// ��ͬʱ�����һ�����֣�������ID
				sessionNameList.remove(i);
				sessionIDList.remove(i);
				sessionNameList.add(session.getAttribute("username").toString());
				sessionIDList.add(session.getId());
				return;
			}
		}
		// ����ͬ�����
		sessionIDList.add(session.getId());
		sessionNameList.add(session.getAttribute("username").toString());
	}
}
