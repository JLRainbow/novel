package com.novel.impl.novel;


import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.novel.entity.Novel;
import com.novel.enums.NovelEnum;
import com.novel.impl.Crawl;
import com.novel.inf.INovel;
import com.novel.util.NovelSpiderUtil;

public abstract class AbstractNovel extends Crawl implements INovel {
	//下一页元素
	protected Element nextPageElement;
	
	// 下一页地址
	protected String nextPageUrl;
	/**
	 * 
	 *方法：默认抓取解析小说列表的<tr>元素，最多会尝试{@link INovel#MAX_TRY_TIMES}次
	 *创建时间：2018年1月8日
	 *创建者：jial
	 */
	protected Elements getTrs(String url) throws Exception{
		
		return getTrs(url,INovel.MAX_TRY_TIMES);
		
	}

	/**
	 * 
	 *方法：以指定次数抓取解析小说列表的<tr>元素
	 *	maxTryTimes为null时，抓取{@link INovel#MAX_TRY_TIMES}次
	 *创建时间：2018年1月8日
	 *创建者：jial
	 */
	protected Elements getTrs(String url,Integer maxTryTimes) throws Exception{
		maxTryTimes = maxTryTimes == null ? INovel.MAX_TRY_TIMES : maxTryTimes;
		for (int i = 0; i < maxTryTimes; i++) {
			
			try {
				String result = super.crawl(url);
				String novelSelector = NovelSpiderUtil.getContext(NovelEnum.getNovelEnumByUrl(url)).get("novel-selector");
				if(novelSelector == null) throw new RuntimeException(NovelEnum.getNovelEnumByUrl(url).getUrl() + ",url:" + url + "目前不支持抓取小说列表");
				Document document = Jsoup.parse(result);
				document.setBaseUri(url);
				// 抓取小说列表<tr>元素
				Elements trs = document.select(novelSelector);
				// 抓取页面下一页元素
				String nextPageSelector = NovelSpiderUtil.getContext(NovelEnum.getNovelEnumByUrl(url)).get("novel-nextPage-selector");
				if(nextPageSelector != null){
					Elements nextPage = document.select(nextPageSelector);
					nextPageElement = nextPage == null ? null : nextPage.first();
					
					if(nextPageElement != null){
						nextPageUrl = nextPageElement.absUrl("href");
					}else{
						nextPageUrl = "";
					}
				}
				return trs;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		throw new RuntimeException(url+",尝试了"+maxTryTimes+"依然下载失败！");
	}
	
	@Override
	public boolean hasNext() {
		return !nextPageUrl.isEmpty();
	}

	@Override
	public String next() {
		return nextPageUrl;
	}

	@Override
	public Iterator<List<Novel>> iterator(String firstPage, Integer maxTryTimes) {
		nextPageUrl = firstPage;
		return new NovelIterator(maxTryTimes);
	}
	
	/**
	 * 抓取小说列表迭代器
	 * @author Administrator
	 *
	 */
	private class NovelIterator implements Iterator<List<Novel>>{
		private Integer maxTryTimes;
		
		public NovelIterator(Integer maxTryTimes){
			this.maxTryTimes = maxTryTimes;
		}
		@Override
		public boolean hasNext() {
			return AbstractNovel.this.hasNext();
		}

		@Override
		public List<Novel> next() {
			List<Novel> novels = getNovels(nextPageUrl,maxTryTimes);
			return novels;
		}
		
	}
}
