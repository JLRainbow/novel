package com.novel.impl.novel;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.novel.entity.Novel;
import com.novel.enums.NovelEnum;
import com.novel.util.NovelSpiderUtil;
/**
 * 笔下文学网站小说列表抓取
 * @author Administrator
 *
 */
public class BXWXNovel extends AbstractNovel {

	
	public BXWXNovel() {
	}

	@Override
	public List<Novel> getNovels(String url, Integer maxTryTimes) {
		List<Novel> novels = new ArrayList<>();
		try {
			Elements trs = super.getTrs(url,maxTryTimes);
			for(int index = 1, size = trs.size(); index < size; index++){ // 不要第一个<tr>元素
				Element tr = trs.get(index);
				Elements tds = tr.getElementsByTag("td"); // 获取<tr>下边的<td>元素
				Novel novel = new Novel();
				novel.setName(tds.first().text());
				novel.setNovelUrl(tds.first().getElementsByTag("a").first().absUrl("href").replace(".htm", "/").replace("/binfo/", "/b/")); //将最后的.htm和binfo换成/和b才是小说章节列表
				novel.setLastUpdateChapter(tds.get(1).text());
				novel.setLastUpdateChapterUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				novel.setAuthor(tds.get(2).text());
				novel.setLastUpdateTime(NovelSpiderUtil.getDate(tds.get(4).text(), "yy-MM-dd"));
				novel.setStatus(NovelSpiderUtil.parseNovelStatus(tds.get(5).text()));
				novel.setPlatfromId(NovelEnum.getNovelEnumByUrl(url).getId());
				novels.add(novel);
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return novels;
	}

	

}
