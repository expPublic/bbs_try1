package package_public;

import java.util.ArrayList;
import java.util.Map;

//������
public class Post{

	private String content;//����
	private String creatingDate;//�������ڡ�
	private String type;//���͡������ǩ
	private ArrayList<Map<String, String>> comment;//������+��������
	private String getContent() {
		return content;
	}
	private void setContent(String content) {
		this.content = content;
	}
	private String getCreatingDate() {
		return creatingDate;
	}
	private void setCreatingDate(String creatingDate) {
		this.creatingDate = creatingDate;
	}
	private String getType() {
		return type;
	}
	private void setType(String type) {
		this.type = type;
	}
	private ArrayList<Map<String, String>> getComment() {
		return comment;
	}
	private void setComment(ArrayList<Map<String, String>> comment) {
		this.comment = comment;
	}
}
