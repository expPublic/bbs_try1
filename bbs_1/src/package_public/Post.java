package package_public;

import java.util.ArrayList;
import java.util.Map;

//帖子类
public class Post{

	private String content;//内容
	private String creatingDate;//创建日期·
	private String type;//类型、主题标签
	private ArrayList<Map<String, String>> comment;//评论人+评论内容
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
