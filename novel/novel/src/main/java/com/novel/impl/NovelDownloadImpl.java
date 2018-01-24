package com.novel.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.novel.config.NovelDownloadConfig;
import com.novel.entity.Chapter;
import com.novel.entity.ChapterDetail;
import com.novel.enums.NovelEnum;
import com.novel.inf.IChapter;
import com.novel.inf.IChapterDetail;
import com.novel.inf.INovelDownload;
import com.novel.util.ChapterDetailFactory;
import com.novel.util.ChapterFactory;
import com.novel.util.NovelSpiderUtil;
/**
 * 如何实现多线程下载任意网站小说下载
 * 1.拿到该网站某小说的说有章节：章节列表
 * 2.通过计算，将这些章节分配给指定数量的线程，让他们去解析，然后保存的文本文件中去。
 * 3.通知主线程，将这些零散的小文件合并成一个大文件，最后将这些分散的小文件散掉。
 * @author Administrator
 *
 */
public class NovelDownloadImpl implements INovelDownload {

	@Override
	public String novelDownload(String url,NovelDownloadConfig config) {
		IChapter chapterImpl = ChapterFactory.getChapterImpl(url);
		List<Chapter> chapters = chapterImpl.getChapters(url);
		
		//计算需要多少线程  假设有2053章节 每个线程下载100章 需要21个线程
		int size = config.getSize();
		int maxThreadNum = (int) Math.ceil(chapters.size()*1.0/size);
		//下载任务分配
		Map<String, List<Chapter>> downloadTaskAlloc = new HashMap<>();
		for (int i = 0; i < maxThreadNum; i++) {
			// i = 0 0-99 
			// i = 1 100-199 
			// i = 2 200-299 
			// ...
			// i = 19 1900-1999
			// i = 20 2000-2052  下标要比章节小于一个
			
			int fromIndex = i * size;
			//只有最后一个线程可能不是下载最大章节数
			int toIndex = i == maxThreadNum - 1 ? chapters.size() : i * size + size ;
//			if(i == maxThreadNum-1){
//				toIndex = chapters.size()-1;
//			}
			downloadTaskAlloc.put(fromIndex+"-"+toIndex, chapters.subList(fromIndex, toIndex));
		}
		//创建线程池  交给线程池处理
		ExecutorService executorService = Executors.newFixedThreadPool(maxThreadNum);
		//遍历downloadTaskAlloc
		Set<String> keySet = downloadTaskAlloc.keySet();
		List<Future<String>> futures = new ArrayList<>();
		//创建下载保存路径 根据不同的小说网站下载到不同的文件夹下去
		String savePath = config.getPath() + "/" +NovelEnum.getNovelEnumByUrl(url).getUrl();
		new File(savePath).mkdirs();
		
		for (String key : keySet) {
			Future<String> future = executorService.submit(new DownloadCallable(savePath+"/"+key+".txt", downloadTaskAlloc.get(key),config.getTryTimes()));
			futures.add(future);
		}
		//所有线程执行完毕后关闭线程池
		executorService.shutdown();
		for (Future<String> future : futures) {
			try {
				System.out.println(future.get()+",下载完成!");
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		//下载完分散文件将文件合并并删除零散文件
		NovelSpiderUtil.multiFileMerge(savePath, null, true);
		return savePath;
	}

}

class DownloadCallable implements Callable<String>{
	
	private List<Chapter> chapters;
	
	private String path;
	
	private int tryTimes;
	
	public DownloadCallable(String path,List<Chapter> chapters,int tryTimes){
		this.path = path;
		this.chapters = chapters;
		this.tryTimes = tryTimes;
	}

	@Override
	public String call() throws Exception {
		try(
			PrintWriter out = new PrintWriter(new File(path),"utf-8")	
		){
			for (Chapter chapter : chapters) {
				IChapterDetail chapterDetailImpl = ChapterDetailFactory.getChapterDetailImpl(chapter.getUrl());
				ChapterDetail chapterDetail = null;
				//防止因为网络等原因  下载某个章节失败而线程卡死
				for(int i = 0; i < tryTimes;i++){ 
					try {
						chapterDetail = chapterDetailImpl.getChapterDetail(chapter.getUrl());
						out.println(chapterDetail.getTitle());
						out.println(chapterDetail.getContent());
						break;
					} catch (RuntimeException e) {
						System.out.println("尝试下载第["+(i+1)+"/"+tryTimes+"]次失败！");
					}
				}
			}
			
		}catch (IOException e) {
			// TODO: handle exception
		}
		return path;
	}
	
}
