package com.novel.inf;

import java.util.List;

import com.novel.entity.Chapter;

public interface IChapter {
	/**
	 * 
	 *����������һ��URL �����½��б�
	 *����ʱ�䣺2017��12��29��
	 *�����ߣ�jial
	 */
	public List<Chapter> getChapters(String url);
}
