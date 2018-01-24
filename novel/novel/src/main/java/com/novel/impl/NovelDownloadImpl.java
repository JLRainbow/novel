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
 * ���ʵ�ֶ��߳�����������վС˵����
 * 1.�õ�����վĳС˵��˵���½ڣ��½��б�
 * 2.ͨ�����㣬����Щ�½ڷ����ָ���������̣߳�������ȥ������Ȼ�󱣴���ı��ļ���ȥ��
 * 3.֪ͨ���̣߳�����Щ��ɢ��С�ļ��ϲ���һ�����ļ��������Щ��ɢ��С�ļ�ɢ����
 * @author Administrator
 *
 */
public class NovelDownloadImpl implements INovelDownload {

	@Override
	public String novelDownload(String url,NovelDownloadConfig config) {
		IChapter chapterImpl = ChapterFactory.getChapterImpl(url);
		List<Chapter> chapters = chapterImpl.getChapters(url);
		
		//������Ҫ�����߳�  ������2053�½� ÿ���߳�����100�� ��Ҫ21���߳�
		int size = config.getSize();
		int maxThreadNum = (int) Math.ceil(chapters.size()*1.0/size);
		//�����������
		Map<String, List<Chapter>> downloadTaskAlloc = new HashMap<>();
		for (int i = 0; i < maxThreadNum; i++) {
			// i = 0 0-99 
			// i = 1 100-199 
			// i = 2 200-299 
			// ...
			// i = 19 1900-1999
			// i = 20 2000-2052  �±�Ҫ���½�С��һ��
			
			int fromIndex = i * size;
			//ֻ�����һ���߳̿��ܲ�����������½���
			int toIndex = i == maxThreadNum - 1 ? chapters.size() : i * size + size ;
//			if(i == maxThreadNum-1){
//				toIndex = chapters.size()-1;
//			}
			downloadTaskAlloc.put(fromIndex+"-"+toIndex, chapters.subList(fromIndex, toIndex));
		}
		//�����̳߳�  �����̳߳ش���
		ExecutorService executorService = Executors.newFixedThreadPool(maxThreadNum);
		//����downloadTaskAlloc
		Set<String> keySet = downloadTaskAlloc.keySet();
		List<Future<String>> futures = new ArrayList<>();
		//�������ر���·�� ���ݲ�ͬ��С˵��վ���ص���ͬ���ļ�����ȥ
		String savePath = config.getPath() + "/" +NovelEnum.getNovelEnumByUrl(url).getUrl();
		new File(savePath).mkdirs();
		
		for (String key : keySet) {
			Future<String> future = executorService.submit(new DownloadCallable(savePath+"/"+key+".txt", downloadTaskAlloc.get(key),config.getTryTimes()));
			futures.add(future);
		}
		//�����߳�ִ����Ϻ�ر��̳߳�
		executorService.shutdown();
		for (Future<String> future : futures) {
			try {
				System.out.println(future.get()+",�������!");
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		//�������ɢ�ļ����ļ��ϲ���ɾ����ɢ�ļ�
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
				//��ֹ��Ϊ�����ԭ��  ����ĳ���½�ʧ�ܶ��߳̿���
				for(int i = 0; i < tryTimes;i++){ 
					try {
						chapterDetail = chapterDetailImpl.getChapterDetail(chapter.getUrl());
						out.println(chapterDetail.getTitle());
						out.println(chapterDetail.getContent());
						break;
					} catch (RuntimeException e) {
						System.out.println("�������ص�["+(i+1)+"/"+tryTimes+"]��ʧ�ܣ�");
					}
				}
			}
			
		}catch (IOException e) {
			// TODO: handle exception
		}
		return path;
	}
	
}
