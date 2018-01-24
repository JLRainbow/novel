package com.storage.impl;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.novel.entity.Novel;
import com.novel.inf.INovel;
import com.novel.util.NovelFactory;
import com.storage.inf.Processor;

public abstract class AbstractNovelStorage implements Processor{

	protected SqlSessionFactory sessionFactory;
	
	protected Map<String,String> tasks = new TreeMap<>();

	public AbstractNovelStorage() throws Exception{
		sessionFactory = new SqlSessionFactoryBuilder().build(new FileInputStream("conf/SqlMapConfig.xml"));
	}
	
	@Override
	public void process() {
		ExecutorService service = Executors.newFixedThreadPool(tasks.size());
		List<Future> futures = new ArrayList<>(tasks.size());
		for (Entry<String, String> entry : tasks.entrySet()) {
			final String key = entry.getKey();
			final String value = entry.getValue();
			futures.add(service.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					INovel novelImpl = NovelFactory.getNovelImpl(value);
					Iterator<List<Novel>> iterator = novelImpl.iterator(value, 5);
					while (iterator.hasNext()) {
						System.out.println("��ʼץȡ["+key+"]��URL��"+novelImpl.next());
						List<Novel> novels = iterator.next();
						for(Novel novel :novels){
							novel.setFirstLetter(key.charAt(0));//����С˵���ֵ�����ĸ
						}
						SqlSession session = sessionFactory.openSession();
						session.insert("batchInsert", novels);
						session.commit();//�ύ����
						session.close();
						Thread.sleep(1_000);//��ֹƵ��ץȡIP����ֹ����
					}
					return key;
				}
			}));
		}
		service.shutdown();
		for (Future<String> future : futures) {
			try {
				System.out.println("ץȡ["+future.get()+"]������");
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
}
