package com.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.novel.entity.Novel;
import com.web.mapper.NovelMapper;
import com.web.service.NovelService;
@Service
public class NovelServiceImpl implements NovelService {

	@Autowired
	private NovelMapper novelMapper;

	@Override
	public List<Novel> getNovelsByKeyword(String keyword,int pageNum,int pageSize) {
		keyword = "%"+keyword+"%";
		return novelMapper.getNovelsByKeyword(keyword);
	}

	@Override
	public List<Novel> getNovelsByKeywordAndPlatform(String keyword, String platformId) {
		Map<String,String> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("platformId", platformId);
		return novelMapper.getNovelsByKeywordAndPlatform(map);
	}

}
