package com.novel.test;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.novel.config.NovelDownloadConfig;
import com.novel.entity.Chapter;
import com.novel.entity.ChapterDetail;
import com.novel.entity.Novel;
import com.novel.enums.NovelEnum;
import com.novel.impl.NovelDownloadImpl;
import com.novel.impl.chapter.BXWXChapterImpl;
import com.novel.impl.chapter.ChapterDetailImpl;
import com.novel.impl.chapter.ChapterImpl;
import com.novel.inf.IChapter;
import com.novel.inf.IChapterDetail;
import com.novel.inf.INovel;
import com.novel.inf.INovelDownload;
import com.novel.util.NovelFactory;
import com.novel.util.NovelSpiderUtil;

public class TestCase {

	@Test
	public void testGetChapters(){
		IChapter chapterImpl = new ChapterImpl();
		List<Chapter> chapters = chapterImpl.getChapters("https://www.bxwx9.org/b/8/8823/index.html");
		for (Chapter chapter : chapters) {
			System.out.println(chapter);
		}
	}
	@Test
	public void testNovelSpiderUtil(){
		System.out.println(NovelSpiderUtil.getContext(NovelEnum.getNovelEnumByUrl("http://www.23wx.com"))); 
		
	}
	@Test
	public void testGetChapterDetail(){
		IChapterDetail chapterDetail = new ChapterDetailImpl();
		ChapterDetail detail = chapterDetail.getChapterDetail("https://www.bxwx9.org/b/8/8823/5789600.html");
		System.out.println(detail);
	}
	@Test
	public void testBXWXChapterImpl(){
		IChapter chapterImpl = new BXWXChapterImpl();
		List<Chapter> chapters = chapterImpl.getChapters("https://www.bxwx9.org/b/8/8823/index.html");
		for (Chapter chapter : chapters) {
			System.out.println(chapter);
		}
	}
	@Test 
	public void testNovelDownload(){
		INovelDownload novelDownload = new NovelDownloadImpl();
		NovelDownloadConfig config = new NovelDownloadConfig();
		config.setPath("e:/novel");
		config.setSize(50);
		System.out.println("下载文件保存在："+novelDownload.novelDownload("https://www.bxwx9.org/b/8/8823/index.html", config));
	}
	@Test 
	public void testMultiFileMerge(){
		NovelSpiderUtil.multiFileMerge("E:/novel", null ,true);
	}
	@Test 
	public void testKanShuZhongNovel(){
		INovel novelImpl = NovelFactory.getNovelImpl("http://www.kanshuzhong.com/booktype/8/1/");
		List<Novel> novels = novelImpl.getNovels("http://www.kanshuzhong.com/booktype/8/1/",5);
		for (Novel novel : novels) {
			System.out.println(novel.toString());
		}
	}
	@Test 
	public void testBXWXNovel(){
		INovel novelImpl = NovelFactory.getNovelImpl("https://www.bxwx9.org/modules/article/index.php?fullflag=1");
		List<Novel> novels = novelImpl.getNovels("https://www.bxwx9.org/modules/article/index.php?fullflag=1",5);
		for (Novel novel : novels) {
			System.out.println(novel.toString());
		}
	}
	@Test 
	public void testNovelIterator(){
		INovel novelImpl = NovelFactory.getNovelImpl("http://www.kanshuzhong.com/map/A/1/");
		Iterator<List<Novel>> iterator = novelImpl.iterator("http://www.kanshuzhong.com/map/A/1/", 5);
		while(iterator.hasNext()){
			List<Novel> novels = iterator.next();
//			for (Novel novel : novels) {
//				System.out.println(novel);
//			}
			// 输出下一页地址
			System.out.println("url:"+novelImpl.next());
		}
	}
}
