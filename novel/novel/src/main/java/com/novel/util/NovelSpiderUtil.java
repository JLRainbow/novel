package com.novel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.novel.enums.NovelEnum;

public final class NovelSpiderUtil {

	private static Map<NovelEnum, Map<String, String>> CONTEXT_MAP = new HashMap<>();
	static {
		init();
	}

	private NovelSpiderUtil() {
	}

	// ��ȡSpider-Rule.xml
	@SuppressWarnings("unchecked")
	private static void init() {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read("conf/Spider-Rule.xml");
			// ��ȡ���ڵ�<sites>
			Element rootElement = document.getRootElement();
			// ��ȡ���ڵ�<sites>�µ�<site>����
			List<Element> sites = rootElement.elements("site");
			for (Element site : sites) {
				// ��ȡ���ڵ�<site>�µ��ӽڵ�
				List<Element> s = site.elements();
				// ����һ���ӽڵ�MAP
				Map<String, String> sMap = new HashMap<>();
				for (Element e : s) {
					// ��ȡ�ӽڵ�ı�ǩ��
					String name = e.getName();
					// ��ȡ�ӽڵ������
					String text = e.getTextTrim();
					// ���ӽڵ�ı�ǩ����Ϊkey��������Ϊvalue�浽map��
					sMap.put(name, text);
				}
				// ���ӽڵ�MAP�ŵ�CONTEXT_MAP��
				CONTEXT_MAP.put(NovelEnum.getNovelEnumByUrl(sMap.get("url")), sMap);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void setConfigPath(String path) {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(path);
			// ��ȡ���ڵ�<sites>
			Element rootElement = document.getRootElement();
			// ��ȡ���ڵ�<sites>�µ�<site>����
			List<Element> sites = rootElement.elements("site");
			for (Element site : sites) {
				// ��ȡ���ڵ�<site>�µ��ӽڵ�
				List<Element> s = site.elements();
				// ����һ���ӽڵ�MAP
				Map<String, String> sMap = new HashMap<>();
				for (Element e : s) {
					// ��ȡ�ӽڵ�ı�ǩ��
					String name = e.getName();
					// ��ȡ�ӽڵ������
					String text = e.getTextTrim();
					// ���ӽڵ�ı�ǩ����Ϊkey��������Ϊvalue�浽map��
					sMap.put(name, text);
				}
				// ���ӽڵ�MAP�ŵ�CONTEXT_MAP��
				CONTEXT_MAP.put(NovelEnum.getNovelEnumByUrl(sMap.get("url")), sMap);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �õ���Ӧ��վ�Ľ������� ���Ⱪ¶һ��������ͨ��һ��ö�٣����ظ����ӽڵ������
	 */
	public static Map<String, String> getContext(NovelEnum novelEnum) {
		return CONTEXT_MAP.get(novelEnum);
	}

	/**
	 * path:����Ŀ¼����Ŀ¼�µ������ļ������ϲ���mregeToFile
	 * mregeToFile�����ϲ����ı��ļ��������������Ϊnull��Ϊnull�ϲ�֮����ļ�������path/merge.txt
	 * deleteThisFile:�Ƿ�ɾ����ɢ�ļ�
	 * ���������ļ��ϲ�Ϊһ���ļ� �ϲ����򣺰��ļ����ָ����� ����ʱ�䣺2018��1��8�� �����ߣ�jial
	 * @param deleteThisFile 
	 */
	public static void multiFileMerge(String path, String mregeToFile, boolean deleteThisFile) {
		mregeToFile = mregeToFile == null ? path + "/merge.txt" : mregeToFile;
		File[] files = new File(path).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// ֻ�ϲ���Ŀ¼����.txt��β���ļ�
				return name.endsWith(".txt");
			}
		});
		// ���ļ���������
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				int o1Index = Integer.parseInt(o1.getName().split("\\-")[0]);
				int o2Index = Integer.parseInt(o2.getName().split("\\-")[0]);
				if (o1Index > o2Index) {
					return 1;
				} else if (o1Index == o2Index) {
					return 0;
				} else {
					return -1;
				}
			}
		});

		PrintWriter out = null;
		try {
			out = new PrintWriter(new File(mregeToFile));
			for (File file : files) {
				BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
				String line = null;
				while ((line = bufr.readLine()) != null) {
					out.println(line);
				}
				bufr.close();
				if(deleteThisFile){
					file.delete();
				}
			}
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}finally {
			out.close();
		}

	}
	/**
	 * 
	 *����������С˵״̬
	 *����ʱ�䣺2018��1��9��
	 *�����ߣ�jial
	 */
	public static int parseNovelStatus(String status){
		if(status.contains("����")){
			return 1;
		}else if(status.contains("���") || status.contains("�걾") || status.contains("���")){
			return 2;
		}
		throw new RuntimeException("��֧�ֵ�С˵״̬"+status);
	}
	/**
	 * 
	 *���������ַ���ʱ��ת����ʱ�����͵�ʱ��
	 *����ʱ�䣺2018��1��9��
	 *�����ߣ�jial
	 */
	public static Date getDate(String dateStr,String pattern) throws ParseException{
		if ("MM-dd".equals(pattern)) {
			pattern = "yyyy-MM-dd";
			Calendar calendar = Calendar.getInstance();
			dateStr = calendar.get(Calendar.YEAR) + "-" + dateStr;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = sdf.parse(dateStr);
		return date;
	}
	
}
