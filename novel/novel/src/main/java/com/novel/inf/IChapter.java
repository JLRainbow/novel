package com.novel.inf;

import java.util.List;

import com.novel.entity.Chapter;

public interface IChapter {
	/**
	 * 
	 *方法：传入一个URL 返回章节列表
	 *创建时间：2017年12月29日
	 *创建者：jial
	 */
	public List<Chapter> getChapters(String url);
}
