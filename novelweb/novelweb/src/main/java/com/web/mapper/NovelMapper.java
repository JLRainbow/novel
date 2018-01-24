package com.web.mapper;

import java.util.List;
import java.util.Map;

import com.novel.entity.Novel;

public interface NovelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Novel record);

    int insertSelective(Novel record);

    Novel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Novel record);

    int updateByPrimaryKey(Novel record);
    
    public void batchInsert(List<Novel> novels);
    
    public List<Novel> getNovelsByKeyword(String keyword);

	public List<Novel> getNovelsByKeywordAndPlatform(Map<String, String> map);
}