package package_scc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import package_public.UserBean;

import java.sql.Connection;

/*
 * �����ṩ
 * JDBC����MySQL
 * �ڱ��в������ݣ�ע�ᣩ
 * ɾ����������
 * ���±������ݣ��޸����롢���Ƹ�����Ϣ�����·�����¼��
 * ��ѯ�������ݣ���֤��¼��
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

	// ����һ�����ݣ���Ҫ����ע��
	public static int insert(UserBean user) {
		String sql = "insert into users(username,password)values(?,?)";
		int i = 0;// 0��ʧ�ܣ���0��ɹ�
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

	// ɾ��ĳ���ݡ��������ô˷���ɾ�����ӡ�ɾ��Υ����Ա�˺ŵ�
	public static void delete() {

	}

	// �����޸ĸ��£�����ID�������޸ģ�email������ȵ������޸�
	public static void update() {

	}

	// ��ѯ������Ŀ��Ҫ������ѯ�������ѯ������� һ������
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

	// ��ѯ������ ר��Ϊ��¼��֤д�ķ�����ֻ�践�ز���ֵ����֤�Ƿ�ͨ��
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
				System.out.println("��¼�ɹ���");
				return true;
			} else {
				System.out.println("��¼ʧ�ܣ�");
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
				System.out.println("�ͷ���Դ�����쳣��");
			}

		}
		return false;
	}

	public static boolean is_can_be_registered(String username) {// ������û����Ƿ����ע��
		UserBean user = new UserBean();
		user.setUsername(username);
		ResultSet rs = query(user);
		try {
			rs.last();
			int length = rs.getRow();
			rs.first();
			if (length > 0) {
				// ע�����ظ�
				return false;
			} else {
				// ���û�������ע��
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
