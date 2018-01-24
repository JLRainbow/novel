package com.novel.impl.chapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.novel.entity.Chapter;

public class BXWXChapterImpl extends ChapterImpl {

	/**
	 * 对笔下文学的章节列表进行比较
	 */
	public List<Chapter> getChapters(String url) {
		List<Chapter> chapters = super.getChapters(url);
		Collections.sort(chapters, new Comparator<Chapter>() {

			@Override
			public int compare(Chapter o1, Chapter o2) {
				String o1Url = o1.getUrl();
				String o2Url = o2.getUrl();
				String orderNum1 = o1Url.substring(o1Url.lastIndexOf("/")+1, o1Url.lastIndexOf("."));
				String orderNum2 = o2Url.substring(o2Url.lastIndexOf("/")+1, o2Url.lastIndexOf("."));
				return orderNum1.compareTo(orderNum2);
			}
		});
		return chapters;
	}
}
