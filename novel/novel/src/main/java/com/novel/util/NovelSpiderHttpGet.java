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
	 *方法：设置默认请求参数
	 *创建时间：2018年1月4日
	 *创建者：jial
	 */
	private void setDefaultReqParameter(){
		this.setConfig(RequestConfig.custom()
//				.setSocketTimeout(2_000)
				.setConnectTimeout(10_000)	//设置服务器连接的超时时间
				.setConnectionRequestTimeout(10_000)	//设置从服务器读取数据的超时时间
				.build());
	}
}
