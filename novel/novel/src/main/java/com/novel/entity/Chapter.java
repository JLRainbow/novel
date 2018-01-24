package com.novel.entity;

import java.io.Serializable;
/**
 * 章节实体类
 * @author Administrator
 *
 */

public class Chapter implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String title;
	
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Chapter [title=" + title + ", url=" + url + "]";
	}
	
	
}
