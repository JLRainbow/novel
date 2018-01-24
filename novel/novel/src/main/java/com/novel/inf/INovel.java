package com.novel.inf;

import java.util.Iterator;
import java.util.List;

import com.novel.entity.Novel;

public interface INovel {
	//抓取某个页面时最大抓取次数
	public static final int MAX_TRY_TIMES = 3;

	/**
	 * 
	 *方法：传入一个URL 返回小说实体集合
	 *maxTryTimes:尝试抓取失败最大次数
	 *创建时间：2018年1月8日
	 *创建者：jial
	 */
	public List<Novel> getNovels(String url, Integer maxTryTimes);
	
	/**
	 * Iterator<Novel> iterator = novels.iterator();
	 * while (iterator.hasNext()) {
	 * 	Novel novel = iterator.next();
	 * 	System.out.println(novel);
	 * }
	 * */
	//存在下一页
	public boolean hasNext();
	//有下一页的话返回下一页地址
	public String next();
	public Iterator<List<Novel>> iterator(String firstPage, Integer maxTryTimes);
}
