package com.novel.util;


import com.novel.enums.NovelEnum;
import com.novel.impl.novel.BXWXNovel;
import com.novel.impl.novel.KanShuZhongNovel;
import com.novel.inf.INovel;

public final class NovelFactory {

	private NovelFactory() {
	}

	public static INovel getNovelImpl(String url){
		NovelEnum novelEnum = NovelEnum.getNovelEnumByUrl(url);
		switch (novelEnum) {
		case KanShuZhong: return new KanShuZhongNovel();
		case BiXiaWenXue: return new BXWXNovel();
		default:throw new RuntimeException(url+",不支持抓取小说列表");
			
		}
		
	}
}
