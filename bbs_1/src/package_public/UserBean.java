package package_public;

import javax.swing.Icon;

//�û���
public class UserBean {
	private int iconId;//�û�ͷ��Id��ʹ��ID�����ļ��л�ȡ����ʾͷ��
	private String username;
	private String password;
	private String sex;// �Ա�
	private String email;// ����
	private String postHistory;//JSON��ʽ���ַ����洢�û��ķ�����¼��ID�š���һ���Ȳ������������ڲ���
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
