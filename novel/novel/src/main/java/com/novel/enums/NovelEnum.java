package com.novel.enums;
/**
 * 支持的小说网站的枚举
 * @author Administrator
 *
 */
public enum NovelEnum {

	DingDianXiaoShuo(1, "23wx.com"),
	BiQuGe(2, "biquge.tw"),
	KanShuZhong(3, "kanshuzhong.com"),
	BiXiaWenXue(4, "bxwx9.org");
	private int id;
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private NovelEnum(int id, String url) {
		this.id = id;
		this.url = url;
	}
	
	public static NovelEnum getNovelEnumById(int id) {
		switch (id) {
		case 1:
			return DingDianXiaoShuo;
		case 2:
			return BiQuGe;
		case 3:
			return KanShuZhong;
		case 4:
			return BiXiaWenXue;
		default:
			throw new RuntimeException("id:" + id + "是不被支持的小说网站");
		}
	}
	
	public static NovelEnum getNovelEnumByUrl(String url) {
		for (NovelEnum e : values()) {
			if(url.contains(e.url)){
				return e;
			}
		}
		throw new RuntimeException("url:" + url + "是不被支持的小说网站");
	}
}
