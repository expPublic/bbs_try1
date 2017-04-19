package package_scc;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

public class DBUtil {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	static {
		driver = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://localhost:3306/db_users";
		username = "root";
		password = "123qwe";
	}
	public static Connection open() {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void  close(Connection connection) {
		if (connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
