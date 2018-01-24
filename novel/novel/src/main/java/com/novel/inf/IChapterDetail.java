package com.novel.inf;

import com.novel.entity.ChapterDetail;

public interface IChapterDetail {

	/**
	 * 
	 *方法：传入一个URL 返回章节内容
	 *创建时间：2018年1月2日
	 *创建者：jial
	 */
	public ChapterDetail getChapterDetail(String url);
}
