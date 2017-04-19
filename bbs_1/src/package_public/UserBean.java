package package_public;

import javax.swing.Icon;

//用户类
public class UserBean {
	private int iconId;//用户头像Id，使用ID遍历文件夹获取并显示头像
	private String username;
	private String password;
	private String sex;// 性别
	private String email;// 邮箱
	private String postHistory;//JSON格式的字符串存储用户的发帖记录的ID号。这一项先不做，留到后期补充
	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPostHistory() {
		return postHistory;
	}

	public void setPostHistory(String postHistory) {
		this.postHistory = postHistory;
	}

}
