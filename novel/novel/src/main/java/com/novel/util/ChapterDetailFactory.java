package com.novel.util;

import com.novel.enums.NovelEnum;
import com.novel.impl.chapter.ChapterDetailImpl;
import com.novel.inf.IChapterDetail;

/**
 * 章节内容工厂
 * @author Administrator
 *
 */
public final class ChapterDetailFactory {

	private ChapterDetailFactory(){
	}
	/**
	 * 
	 *方法：通过URL，返回一个实现了IChapterDetail接口的实现类
	 *创建时间：2018年1月3日
	 *创建者：jial
	 */
	public static IChapterDetail getChapterDetailImpl(String url){
		NovelEnum novelEnum = NovelEnum.getNovelEnumByUrl(url);
		IChapterDetail chapterDetail = null;
		switch (novelEnum) {
		case DingDianXiaoShuo:
		case BiQuGe:
		case KanShuZhong:
		case BiXiaWenXue:
			chapterDetail = new ChapterDetailImpl();
			break;

		}
		return chapterDetail;
		
	}
	
}
