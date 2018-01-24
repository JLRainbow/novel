package com.novel.impl.chapter;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.novel.entity.ChapterDetail;
import com.novel.enums.NovelEnum;
import com.novel.impl.Crawl;
import com.novel.inf.IChapterDetail;
import com.novel.util.NovelSpiderUtil;

public class ChapterDetailImpl extends Crawl implements IChapterDetail {

	@Override
	public ChapterDetail getChapterDetail(String url) {
		try {
			String result = crawl(url);
			//过滤问号 将网页的&nbsp;转换成空格  Jsoup不支持换行  所以将换行先转换成特殊字符  最后获取内容的时候才能转换过来
			result = result.replace("&nbsp;", " ").replace("<br />", "${line}").replace("<br/>", "${line}");
			Document document = Jsoup.parse(result);
			document.setBaseUri(url);
			Map<String, String> context = NovelSpiderUtil.getContext(NovelEnum.getNovelEnumByUrl(url));
			ChapterDetail chapterDetail = new ChapterDetail();
			//拿标题
			String titleSelector = context.get("chapter-detail-title-selector");
			String[] splits = titleSelector.split(",");
			splits = parseSelector(splits);
			chapterDetail.setTitle(document.select(splits[0]).get(Integer.parseInt(splits[1])).text());
			//拿章节内容  并将换行的特殊字符转成\n换行
			String contentSelector = context.get("chapter-detail-content-selector");
			chapterDetail.setContent(document.select(contentSelector).text().replace("${line}", "\n"));
			//上一页地址
			String prevSelector = context.get("chapter-detail-prev-selector");
			splits = prevSelector.split(",");
			splits = parseSelector(splits);
			chapterDetail.setPrev(document.select(splits[0]).get(Integer.parseInt(splits[1])).absUrl("href"));
			//后一页地址
			String nextSelector = context.get("chapter-detail-next-selector");
			splits = nextSelector.split(",");
			splits = parseSelector(splits);
			chapterDetail.setNext(document.select(splits[0]).get(Integer.parseInt(splits[1])).absUrl("href"));
			
			return chapterDetail;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String[] parseSelector(String[] splits){
		String[] newSplits = new String[2];
		if(splits.length==1){
			newSplits[0] = splits[0];
			newSplits[1] = "0";
			return newSplits;
		}else{
			return splits;
		}
		
	}
}
