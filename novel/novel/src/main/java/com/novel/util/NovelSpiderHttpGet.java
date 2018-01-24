package com.novel.util;

import java.net.URI;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;

public class NovelSpiderHttpGet extends HttpGet {

	public NovelSpiderHttpGet() {
	}

	public NovelSpiderHttpGet(URI uri) {
		super(uri);
	}

	public NovelSpiderHttpGet(String uri) {
		super(uri);
		setDefaultReqParameter();
	}

	/**
	 * 
	 *����������Ĭ���������
	 *����ʱ�䣺2018��1��4��
	 *�����ߣ�jial
	 */
	private void setDefaultReqParameter(){
		this.setConfig(RequestConfig.custom()
//				.setSocketTimeout(2_000)
				.setConnectTimeout(10_000)	//���÷��������ӵĳ�ʱʱ��
				.setConnectionRequestTimeout(10_000)	//���ôӷ�������ȡ���ݵĳ�ʱʱ��
				.build());
	}
}
