package package_scc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import package_public.UserBean;

import java.sql.Connection;

/*
 * 该类提供
 * JDBC连接MySQL
 * 在表中插入数据（注册）
 * 删除表中数据
 * 更新表中数据（修改密码、完善个人信息、更新发帖记录）
 * 查询表中数据（验证登录）
 * */
public class DBUtil {
	private static String driver;
	private static String url;
	private static String sqlusername;
	private static String sqlpassword;
	static {
		driver = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://localhost:3306/db_users";
		sqlusername = "root";
		sqlpassword = "123qwe";
	}

	public static Connection open() {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, sqlusername, sqlpassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 插入一条数据，主要用于注册
	public static int insert(UserBean user) {
		String sql = "insert into users(username,password)values(?,?)";
		int i = 0;// 0则失败，非0则成功
		Connection connection = open();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, sqlusername);
			preparedStatement.setString(2, sqlpassword);
			i = preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 删除某数据。用于利用此方法删除帖子、删除违法会员账号等
	public static void delete() {

	}

	// 数据修改更新，帖子ID的增加修改，email、密码等的完善修改
	public static void update() {

	}

	// 查询操作，目的要看到查询结果，查询结果返回 一个集合
	public static ResultSet query(UserBean user) {
		String sql = "select * from users where username=?";
		Connection connection = open();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUsername());
			rs = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	// 查询操作， 专门为登录验证写的方法，只需返回布尔值，验证是否通过
	public static boolean query_forLogin(UserBean user) {
		String sql = "select * from users where username=? and password=?";
		Connection connection = DBUtil.open();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				System.out.println("登录成功！");
				return true;
			} else {
				System.out.println("登录失败！");
				return false;
			}
		} catch (SQLException e) {
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

	public static boolean is_can_be_registered(String username) {// 检验该用户名是否可以注册
		UserBean user = new UserBean();
		user.setUsername(username);
		ResultSet rs = query(user);
		try {
			rs.last();
			int length = rs.getRow();
			rs.first();
			if (length > 0) {
				// 注册名重复
				return false;
			} else {
				// 该用户名可以注册
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
