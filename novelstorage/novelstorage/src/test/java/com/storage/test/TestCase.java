package com.storage.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.storage.impl.BXWXNovelStorageProcess;
import com.storage.impl.KanShuZhongNovelStorageProcess;
import com.storage.inf.Processor;

public class TestCase {


	@Test
	public void testSqlMapConfig() throws FileNotFoundException{
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(new FileInputStream("conf/SqlMapConfig.xml"));
		System.out.println(sessionFactory);
	}
	
	@Test
	public void testKanShuZhongNovelStorageProcess() throws Exception{
		Processor process = new KanShuZhongNovelStorageProcess();
		process.process();
	}
	
	@Test
	public void testBXWXNovelStorageProcess() throws Exception{
		Processor process = new BXWXNovelStorageProcess();
		process.process();
	}
}
