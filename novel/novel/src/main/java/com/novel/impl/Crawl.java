package com.novel.impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.novel.enums.NovelEnum;
import com.novel.util.NovelSpiderHttpGet;
import com.novel.util.NovelSpiderUtil;

public class Crawl {

	/**
	 * 
	 *方法：抓取指定网站的内容（章节和章节内容）
	 *创建时间：2018年1月2日
	 *创建者：jial
	 */
	protected String crawl(String url){
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			CloseableHttpResponse httpResponse = httpClient.execute(new NovelSpiderHttpGet(url));){
			String result = EntityUtils.toString(httpResponse.getEntity(),NovelSpiderUtil.getContext(NovelEnum.getNovelEnumByUrl(url)).get("charset"));
			return result;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
