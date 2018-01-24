package com.novel.inf;

import java.util.Iterator;
import java.util.List;

import com.novel.entity.Novel;

public interface INovel {
	//ץȡĳ��ҳ��ʱ���ץȡ����
	public static final int MAX_TRY_TIMES = 3;

	/**
	 * 
	 *����������һ��URL ����С˵ʵ�弯��
	 *maxTryTimes:����ץȡʧ��������
	 *����ʱ�䣺2018��1��8��
	 *�����ߣ�jial
	 */
	public List<Novel> getNovels(String url, Integer maxTryTimes);
	
	/**
	 * Iterator<Novel> iterator = novels.iterator();
	 * while (iterator.hasNext()) {
	 * 	Novel novel = iterator.next();
	 * 	System.out.println(novel);
	 * }
	 * */
	//������һҳ
	public boolean hasNext();
	//����һҳ�Ļ�������һҳ��ַ
	public String next();
	public Iterator<List<Novel>> iterator(String firstPage, Integer maxTryTimes);
}
