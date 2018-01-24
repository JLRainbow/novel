package com.storage;

import com.storage.impl.BXWXNovelStorageProcess;
import com.storage.impl.KanShuZhongNovelStorageProcess;
import com.storage.inf.Processor;
/**
 * ������
 * @author Administrator
 *
 */
public class Bootstrap {

	
	public static void main(String[] args) throws Exception {
		Processor process = new KanShuZhongNovelStorageProcess();
		process.process();
		process = new BXWXNovelStorageProcess();
		process.process();

	}

}
