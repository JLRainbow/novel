package com.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.novel.entity.Chapter;
import com.novel.entity.ChapterDetail;
import com.novel.entity.Novel;
import com.novel.inf.IChapter;
import com.novel.inf.IChapterDetail;
import com.novel.util.ChapterDetailFactory;
import com.novel.util.ChapterFactory;
import com.novel.util.NovelSpiderUtil;
import com.web.entity.JSONResponse;
import com.web.service.NovelService;

@Controller
public class NovelController {

	static{
		NovelSpiderUtil.setConfigPath("E:/novel/novelweb/conf/Spider-Rule.xml");
	}
	@Autowired
	private NovelService novelService;
	
	@RequestMapping(value = "getChapters.do",method = RequestMethod.GET)
	@ResponseBody
	public JSONResponse getChapters(String url){
		IChapter chapterImpl = ChapterFactory.getChapterImpl(url);
		List<Chapter> chapters = chapterImpl.getChapters(url);
		return JSONResponse.success(chapters);
	}
	
	@RequestMapping(value = "getChapterDetail.do",method = RequestMethod.GET)
	@ResponseBody
	public JSONResponse getChapterDetail(String url){
		IChapterDetail chapterDetailImpl = ChapterDetailFactory.getChapterDetailImpl(url);
		ChapterDetail chapterDetail = chapterDetailImpl.getChapterDetail(url);
		return JSONResponse.success(chapterDetail);
	}
	
	@RequestMapping(value = "getNovelsByKeyword.do",method = RequestMethod.POST)
	@ResponseBody
	public JSONResponse getNovelsByKeyword(@RequestParam(value = "keyword",required =false)String keyword
			,int pageNum,int pageSize,HttpServletRequest req){
		pageNum = pageNum == 0 ? 1 : pageNum;
		pageNum = pageNum == 0 ? 5 : pageNum;
		Page page = PageHelper.startPage(pageNum,pageSize);
		List<Novel> novelList = novelService.getNovelsByKeyword(keyword,pageNum,pageSize);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("novelList", novelList);
		data.put("pages", page.getPages());
		return JSONResponse.success(data);
	}
	
	@RequestMapping(value = "getNovelsByKeywordAndPlatform.do",method = RequestMethod.POST)
	@ResponseBody
	public JSONResponse getNovelsByKeywordAndPlatform(@RequestParam(value = "keyword",required =false)String keyword,
			@RequestParam(value = "platformId",required =false)String platformId){
		return JSONResponse.success(novelService.getNovelsByKeywordAndPlatform(keyword,platformId));
	}
	
	@RequestMapping(value = "showChapterList.do",method = RequestMethod.GET)
	public ModelAndView showChapterList(String url){
		ModelAndView mv = new ModelAndView();
		List<Chapter> chapters = null;
		try {
			IChapter chapterImpl = ChapterFactory.getChapterImpl(url);
			chapters = chapterImpl.getChapters(url);
			mv.getModel().put("isSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			mv.getModel().put("isSuccess", false);
		}
		mv.setViewName("chapterList");
		mv.getModel().put("chapters", chapters);
		mv.getModel().put("chapterListUrl", url);
		return mv;
	}
	@RequestMapping(value = "showChapterDetail.do",method = RequestMethod.GET)
	public ModelAndView showChapterDetail(String url,String chapterListUrl){
		ModelAndView mv = new ModelAndView();
		ChapterDetail chapterDetail = null;
		try {
			IChapterDetail chapterDetailImpl = ChapterDetailFactory.getChapterDetailImpl(url);
			chapterDetail = chapterDetailImpl.getChapterDetail(url);
			chapterDetail.setContent(chapterDetail.getContent().replace("\n", "<br>"));
			mv.getModel().put("isSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			mv.getModel().put("isSuccess", false);
		}
		mv.setViewName("chapterDetail");
		mv.getModel().put("chapterDetail", chapterDetail);
		mv.getModel().put("chapterListUrl", chapterListUrl);
		return mv;
	}
}
