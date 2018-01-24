package com.novel.util;

import com.novel.enums.NovelEnum;
import com.novel.impl.chapter.BXWXChapterImpl;
import com.novel.impl.chapter.ChapterImpl;
import com.novel.inf.IChapter;
/**
 * �½��б���
 * @author Administrator
 *
 */
public final class ChapterFactory {

	private ChapterFactory() {
	}

	/**
	 * 
	 *������ͨ��URL������һ��ʵ����IChapter�ӿڵ�ʵ����
	 *����ʱ�䣺2018��1��3��
	 *�����ߣ�jial
	 */
	public static IChapter getChapterImpl(String url){
		NovelEnum novelEnum = NovelEnum.getNovelEnumByUrl(url);
		IChapter chapter = null;
		switch (novelEnum) {
		case DingDianXiaoShuo:
		case BiQuGe:
		case KanShuZhong:
			chapter = new ChapterImpl();
			break;
		case BiXiaWenXue:
			chapter = new BXWXChapterImpl();
			break;

		}
		return chapter;
		
	}
	
}
