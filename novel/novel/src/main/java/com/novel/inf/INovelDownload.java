package com.novel.inf;

import com.novel.config.NovelDownloadConfig;

public interface INovelDownload {

	/**
	 * 返回值是下载地址 ： 比如说我下载到D:/novel/biquge.tw/完美世界/完美世界.txt
	 * 传入的url是指某本小说的章节列表页面
	 *方法：小说下载
	 *创建时间：2018年1月3日
	 *创建者：jial
	 */
	public String novelDownload(String url,NovelDownloadConfig config);
}
