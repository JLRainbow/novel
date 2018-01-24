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

	// 读取Spider-Rule.xml
	@SuppressWarnings("unchecked")
	private static void init() {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read("conf/Spider-Rule.xml");
			// 获取根节点<sites>
			Element rootElement = document.getRootElement();
			// 获取根节点<sites>下的<site>集合
			List<Element> sites = rootElement.elements("site");
			for (Element site : sites) {
				// 获取根节点<site>下的子节点
				List<Element> s = site.elements();
				// 声明一个子节点MAP
				Map<String, String> sMap = new HashMap<>();
				for (Element e : s) {
					// 获取子节点的标签名
					String name = e.getName();
					// 获取子节点的内容
					String text = e.getTextTrim();
					// 将子节点的标签名作为key，内容作为value存到map里
					sMap.put(name, text);
				}
				// 将子节点MAP放到CONTEXT_MAP里
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
			// 获取根节点<sites>
			Element rootElement = document.getRootElement();
			// 获取根节点<sites>下的<site>集合
			List<Element> sites = rootElement.elements("site");
			for (Element site : sites) {
				// 获取根节点<site>下的子节点
				List<Element> s = site.elements();
				// 声明一个子节点MAP
				Map<String, String> sMap = new HashMap<>();
				for (Element e : s) {
					// 获取子节点的标签名
					String name = e.getName();
					// 获取子节点的内容
					String text = e.getTextTrim();
					// 将子节点的标签名作为key，内容作为value存到map里
					sMap.put(name, text);
				}
				// 将子节点MAP放到CONTEXT_MAP里
				CONTEXT_MAP.put(NovelEnum.getNovelEnumByUrl(sMap.get("url")), sMap);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 拿到对应网站的解析规则 对外暴露一个方法，通过一个枚举，返回给你子节点的内容
	 */
	public static Map<String, String> getContext(NovelEnum novelEnum) {
		return CONTEXT_MAP.get(novelEnum);
	}

	/**
	 * path:基础目录，该目录下的所有文件将被合并到mregeToFile
	 * mregeToFile：被合并的文本文件，这个参数可以为null，为null合并之后的文件保存在path/merge.txt
	 * deleteThisFile:是否删除零散文件
	 * 方法：多文件合并为一个文件 合并规则：按文件名分隔排序 创建时间：2018年1月8日 创建者：jial
	 * @param deleteThisFile 
	 */
	public static void multiFileMerge(String path, String mregeToFile, boolean deleteThisFile) {
		mregeToFile = mregeToFile == null ? path + "/merge.txt" : mregeToFile;
		File[] files = new File(path).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// 只合并该目录下以.txt结尾的文件
				return name.endsWith(".txt");
			}
		});
		// 对文件进行排序
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
	 *方法：解析小说状态
	 *创建时间：2018年1月9日
	 *创建者：jial
	 */
	public static int parseNovelStatus(String status){
		if(status.contains("连载")){
			return 1;
		}else if(status.contains("完结") || status.contains("完本") || status.contains("完成")){
			return 2;
		}
		throw new RuntimeException("不支持的小说状态"+status);
	}
	/**
	 * 
	 *方法：将字符串时间转换成时间类型的时间
	 *创建时间：2018年1月9日
	 *创建者：jial
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
