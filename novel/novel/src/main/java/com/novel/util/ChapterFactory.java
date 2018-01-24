package com.novel.util;

import com.novel.enums.NovelEnum;
import com.novel.impl.chapter.BXWXChapterImpl;
import com.novel.impl.chapter.ChapterImpl;
import com.novel.inf.IChapter;
/**
 * 章节列表工厂
 * @author Administrator
 *
 */
public final class ChapterFactory {

	private ChapterFactory() {
	}

	/**
	 * 
	 *方法：通过URL，返回一个实现了IChapter接口的实现类
	 *创建时间：2018年1月3日
	 *创建者：jial
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
