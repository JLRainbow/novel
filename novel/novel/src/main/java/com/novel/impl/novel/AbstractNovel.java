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
	//��һҳԪ��
	protected Element nextPageElement;
	
	// ��һҳ��ַ
	protected String nextPageUrl;
	/**
	 * 
	 *������Ĭ��ץȡ����С˵�б��<tr>Ԫ�أ����᳢��{@link INovel#MAX_TRY_TIMES}��
	 *����ʱ�䣺2018��1��8��
	 *�����ߣ�jial
	 */
	protected Elements getTrs(String url) throws Exception{
		
		return getTrs(url,INovel.MAX_TRY_TIMES);
		
	}

	/**
	 * 
	 *��������ָ������ץȡ����С˵�б��<tr>Ԫ��
	 *	maxTryTimesΪnullʱ��ץȡ{@link INovel#MAX_TRY_TIMES}��
	 *����ʱ�䣺2018��1��8��
	 *�����ߣ�jial
	 */
	protected Elements getTrs(String url,Integer maxTryTimes) throws Exception{
		maxTryTimes = maxTryTimes == null ? INovel.MAX_TRY_TIMES : maxTryTimes;
		for (int i = 0; i < maxTryTimes; i++) {
			
			try {
				String result = super.crawl(url);
				String novelSelector = NovelSpiderUtil.getContext(NovelEnum.getNovelEnumByUrl(url)).get("novel-selector");
				if(novelSelector == null) throw new RuntimeException(NovelEnum.getNovelEnumByUrl(url).getUrl() + ",url:" + url + "Ŀǰ��֧��ץȡС˵�б�");
				Document document = Jsoup.parse(result);
				document.setBaseUri(url);
				// ץȡС˵�б�<tr>Ԫ��
				Elements trs = document.select(novelSelector);
				// ץȡҳ����һҳԪ��
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
		throw new RuntimeException(url+",������"+maxTryTimes+"��Ȼ����ʧ�ܣ�");
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
	 * ץȡС˵�б������
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
