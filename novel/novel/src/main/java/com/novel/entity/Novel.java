package com.novel.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 小说实体
 * @author Administrator
 *
 */
public class Novel implements Serializable {

	private static final long serialVersionUID = 1L;
	// 书名
	private String name;
	// 作者名
	private String author;
	// 小说链接
	private String novelUrl;
	// 小说类别 ：武侠修真 都市言情
	private String type;
	// 最新章节名称
	private String lastUpdateChapter;
	// 最新章节链接
	private String lastUpdateChapterUrl;
	// 小说最后更新时间
	private Date lastUpdateTime;
	// 小说状态 1：连载 2：完结
	private int status;
	// 书名首字母
	private char firstLetter;
	// 小说平台id
	private int platfromId;
	// 小说存储到我们数据库的时间
	private Date addTime;
	
	private long id;

	public Novel() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public String getNovelUrl() {
		return novelUrl;
	}

	public void setNovelUrl(String novelUrl) {
		this.novelUrl = novelUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLastUpdateChapter() {
		return lastUpdateChapter;
	}

	public void setLastUpdateChapter(String lastUpdateChapter) {
		this.lastUpdateChapter = lastUpdateChapter;
	}

	public String getLastUpdateChapterUrl() {
		return lastUpdateChapterUrl;
	}

	public void setLastUpdateChapterUrl(String lastUpdateChapterUrl) {
		this.lastUpdateChapterUrl = lastUpdateChapterUrl;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public char getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(char firstLetter) {
		this.firstLetter = firstLetter;
	}

	public int getPlatfromId() {
		return platfromId;
	}

	public void setPlatfromId(int platfromId) {
		this.platfromId = platfromId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Novel [name=" + name + ", author=" + author + ", novelUrl=" + novelUrl + ", type=" + type
				+ ", lastUpdateChapter=" + lastUpdateChapter + ", lastUpdateChapterUrl=" + lastUpdateChapterUrl
				+ ", lastUpdateTime=" + lastUpdateTime + ", status=" + status + ", firstLetter=" + firstLetter
				+ ", platfromId=" + platfromId + ", addTime=" + addTime + "]";
	}

	

}
