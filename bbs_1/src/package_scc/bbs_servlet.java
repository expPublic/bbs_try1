package package_scc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class bbs_servlet
 */
@WebServlet("/bbs_servlet")
public class bbs_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bbs_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("inin");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		System.out.println(username+password);
		PrintWriter out = response.getWriter();
		out.println("ss");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);//如果不用GET方法可以把它注释掉
		//获取来自LoginPage页面的账号密码进行数据库验证
/*		System.out.println("inin");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		System.out.println(username+password);
		PrintWriter out = response.getWriter();
		out.println("ss");*/
		
	}

}
