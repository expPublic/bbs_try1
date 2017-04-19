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
		// ��ȡ����LoginPageҳ����˺�����������ݿ���֤

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
		// System.out.println("ע��ɹ�");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// System.out.println(username+"\n"+password);
		boolean YorN = is_can_be_registered(username);
		PrintWriter out = response.getWriter();
		if (YorN) {// ����ע��
			int isSuccess = addUserData(username, password);
			if (isSuccess == 0) {// fail �������С

				out.println("<script>alert('����');window.history.go(-1)</script>");
			} else {// success
				System.out.println(isSuccess);
				response.sendRedirect("LoginPage.jsp");
			}
		} else {
			// �Ѿ���ע����
			out.println("<script>alert('���û����ѱ�ע��');window.history.go(-1)</script>");
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();

		boolean isSuccess = checkWetherExists(username, password);//�������ݿ����Ƿ���ڸ��˺�����
		PrintWriter out = response.getWriter();
		if (!isSuccess) {
			out.println("<script>alert('�˺��������');window.history.go(-1)</script>");// ��½ʧ��
		} else {
			session.setAttribute("username", username);
			session.setAttribute("ID", session.getId());
			addSession(session);//�����򲻲���������������ӽ�ȥ��
			System.out.println(sessionNameList+"  "+sessionIDList);
//			boolean isLogined=checkWetherLogined(username,session.getId());//������˺��Ƿ��ڱ𴦵�¼���𴦵�¼��IDֵ���˴�һ����ͬ
			boolean isLogined=true;
			if (!isLogined) {
				//���˻��Ѿ��ڱ𴦵�¼
				out.println("<script>alert('���˺����ڱ𴦵�¼');window.history.go(-1)</script>");// ��½ʧ��
			}else {
				
				request.getRequestDispatcher("../index.jsp").forward(request, response);
			}
			
		}
	}

	private boolean checkWetherLogined(String username,String ID) {//�÷�����ʱ����
		// TODO Auto-generated method stub
		for (int i = 0; i < sessionNameList.size(); i++) {
			String sampleName = sessionNameList.get(i);
			String sampleID=sessionIDList.get(i);
			if (username==sampleName&&sampleID!=ID) {
				//��¼����ͬ����sessionID��ͬ���������¼����false
				return false;
			}
			
		}
		return true;//����������true
	}
	public boolean checkWetherExists(String username, String password) {//��֤��¼���������˺������Ƿ������ݿ���ĳ�ֶζ�Ӧ
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
				System.out.println("��¼�ɹ���");
				return true;
			} else {
				System.out.println("��¼ʧ�ܣ�");
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
				System.out.println("�ͷ���Դ�����쳣��");
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
				//��ͬʱ�����һ�����֣�������ID
				sessionNameList.remove(i);
				sessionIDList.remove(i);
				sessionNameList.add(session.getAttribute("username").toString());
				sessionIDList.add(session.getId());
				return;
			}
		}
		//����ͬ�����
		sessionIDList.add(session.getId());
		sessionNameList.add(session.getAttribute("username").toString());
	}

	public boolean is_can_be_registered(String username) {//������û����Ƿ����ע��
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
				// ע�����ظ�
				preparedStatement.close();
				connection.close();
				return false;
			} else {
				// ���û�������ע��
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

	

	public int addUserData(String username, String password) {// ע���û������ݿ�
		String sql = "insert into users(username,password)values(?,?)";
		int i = 0;// 0��ʧ�ܣ���0��ɹ�
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
