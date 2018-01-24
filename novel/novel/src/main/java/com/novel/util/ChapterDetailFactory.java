package com.novel.util;

import com.novel.enums.NovelEnum;
import com.novel.impl.chapter.ChapterDetailImpl;
import com.novel.inf.IChapterDetail;

/**
 * �½����ݹ���
 * @author Administrator
 *
 */
public final class ChapterDetailFactory {

	private ChapterDetailFactory(){
	}
	/**
	 * 
	 *������ͨ��URL������һ��ʵ����IChapterDetail�ӿڵ�ʵ����
	 *����ʱ�䣺2018��1��3��
	 *�����ߣ�jial
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
