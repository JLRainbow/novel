package com.novel.config;

import java.io.Serializable;

public class NovelDownloadConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	//ÿ���߳�Ĭ�Ͽ������ص�����½�����
	private static final int DEFAULT_SIZE = 100;
	//ÿ���߳�����ÿһ�������������Դ���
	private static final int DEFAULT_TRY_TIMES = 3;
	//���غ��ļ�����Ļ���ַ ���� D:/novel/bxwx.org/��������/1-2.txt ...
	private String path;
	//ÿ���߳����ص�����½�����
	private int size;
	//���Դ���
	private int tryTimes;
	
	public NovelDownloadConfig() {
		this.size = DEFAULT_SIZE;
		this.tryTimes = DEFAULT_TRY_TIMES;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTryTimes() {
		return tryTimes;
	}
	public void setTryTimes(int tryTimes) {
		this.tryTimes = tryTimes;
	}
	
	
}
