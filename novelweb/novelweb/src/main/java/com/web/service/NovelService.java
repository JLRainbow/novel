package com.web.service;

import java.util.List;

import com.novel.entity.Novel;

public interface NovelService {

	/**
	 * 
	 *���������ݹؼ�������С˵
	 *����ʱ�䣺2018��1��11��
	 *�����ߣ�jial
	 */
	public List<Novel> getNovelsByKeyword(String keyword,int pageNum,int pageSize);

	/**
	 * 
	 *���������ݹؼ��ֺ�ָ����վ����С˵
	 *����ʱ�䣺2018��1��11��
	 *�����ߣ�jial
	 */
	public List<Novel> getNovelsByKeywordAndPlatform(String keyword, String platformId);
}
