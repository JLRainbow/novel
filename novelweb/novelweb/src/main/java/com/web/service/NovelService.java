package com.web.service;

import java.util.List;

import com.novel.entity.Novel;

public interface NovelService {

	/**
	 * 
	 *方法：根据关键字搜索小说
	 *创建时间：2018年1月11日
	 *创建者：jial
	 */
	public List<Novel> getNovelsByKeyword(String keyword,int pageNum,int pageSize);

	/**
	 * 
	 *方法：根据关键字和指定网站搜索小说
	 *创建时间：2018年1月11日
	 *创建者：jial
	 */
	public List<Novel> getNovelsByKeywordAndPlatform(String keyword, String platformId);
}
