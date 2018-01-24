package com.novel.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * С˵ʵ��
 * @author Administrator
 *
 */
public class Novel implements Serializable {

	private static final long serialVersionUID = 1L;
	// ����
	private String name;
	// ������
	private String author;
	// С˵����
	private String novelUrl;
	// С˵��� ���������� ��������
	private String type;
	// �����½�����
	private String lastUpdateChapter;
	// �����½�����
	private String lastUpdateChapterUrl;
	// С˵������ʱ��
	private Date lastUpdateTime;
	// С˵״̬ 1������ 2�����
	private int status;
	// ��������ĸ
	private char firstLetter;
	// С˵ƽ̨id
	private int platfromId;
	// С˵�洢���������ݿ��ʱ��
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
