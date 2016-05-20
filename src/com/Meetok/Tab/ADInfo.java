package com.Meetok.Tab;

import java.io.Serializable;

/**
 * 描述：广告信�?</br>
 * @author Eden Cheng</br>
 * @version 2015�?4�?23�? 上午11:32:53
 */
public class ADInfo implements Serializable{
	private static final long serialVersionUID = -6470574927973900913L;
	String id = "";
	String url = "";
	String content = "";
	String type = "";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
