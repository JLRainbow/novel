package com.novel.impl.chapter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.novel.entity.Chapter;
import com.novel.enums.NovelEnum;
import com.novel.impl.Crawl;
import com.novel.inf.IChapter;
import com.novel.util.NovelSpiderUtil;

public class ChapterImpl extends Crawl implements IChapter {

	@Override
	public List<Chapter> getChapters(String url) {
		try {
			String result = crawl(url);
			Document document = Jsoup.parse(result);
			document.setBaseUri(url);
			Elements elements = document.select(NovelSpiderUtil.getContext(NovelEnum.getNovelEnumByUrl(url)).get("chapter-list-selector"));
			List<Chapter> chapters = new ArrayList<Chapter>();
			for (Element e : elements) {
				Chapter chapter = new Chapter();
				chapter.setTitle(e.text());
				e.absUrl(url);
				chapter.setUrl(e.absUrl("href"));
				chapters.add(chapter);
			}
			return chapters;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
