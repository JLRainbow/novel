package com.novel.inf;

import com.novel.config.NovelDownloadConfig;

public interface INovelDownload {

	/**
	 * ����ֵ�����ص�ַ �� ����˵�����ص�D:/novel/biquge.tw/��������/��������.txt
	 * �����url��ָĳ��С˵���½��б�ҳ��
	 *������С˵����
	 *����ʱ�䣺2018��1��3��
	 *�����ߣ�jial
	 */
	public String novelDownload(String url,NovelDownloadConfig config);
}
