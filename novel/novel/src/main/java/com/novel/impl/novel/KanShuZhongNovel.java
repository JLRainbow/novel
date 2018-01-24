package com.novel.impl.novel;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.novel.entity.Novel;
import com.novel.enums.NovelEnum;
import com.novel.util.NovelSpiderUtil;
/**
 * ��������վС˵�б�ץȡ
 * @author Administrator
 *
 */
public class KanShuZhongNovel extends AbstractNovel {

	public KanShuZhongNovel() {
	}

	@Override
	public List<Novel> getNovels(String url,Integer maxTryTimes) {
		List<Novel> novels = new ArrayList<>();
		try {
			Elements trs = super.getTrs(url,maxTryTimes);
			for(int index = 1,size = trs.size(); index < size - 1; index++){  // ��Ҫ��һ�������һ��<tr>Ԫ��
				Element tr = trs.get(index);
				Elements tds = tr.getElementsByTag("td");
				Novel novel = new Novel();
				novel.setType(tds.first().text());
				novel.setName(tds.get(1).text());
				novel.setNovelUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				novel.setLastUpdateChapter(tds.get(2).text());
				novel.setLastUpdateChapterUrl(tds.get(2).getElementsByTag("a").first().absUrl("href"));
				novel.setAuthor(tds.get(3).text());
				novel.setLastUpdateTime(NovelSpiderUtil.getDate(tds.get(4).text(), "MM-dd"));
				novel.setStatus(NovelSpiderUtil.parseNovelStatus(tds.get(5).text()));
				novel.setPlatfromId(NovelEnum.getNovelEnumByUrl(url).getId());
				novels.add(novel);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return novels;
	}

}


