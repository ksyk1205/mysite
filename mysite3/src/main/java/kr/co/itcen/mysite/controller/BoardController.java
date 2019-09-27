package kr.co.itcen.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(Model model) {
		int page=1;
		String keyword="";
		List<BoardVo> list = boardService.getList(page, keyword);
		Boolean t_count = boardService.count(keyword);
		int total_count = 0 ;
		System.out.println(total_count);
		model.addAttribute("list",list);
		model.addAttribute("total_count",total_count);
		model.addAttribute("pagenumber",((page-1)/5)*5);
		model.addAttribute("index",total_count-((page-1)*5));
		model.addAttribute("page",page);
		model.addAttribute("keyword",keyword);
		System.out.println(total_count);
		return "/board/list";
	}
	
	@RequestMapping("/list")
	public String list(Model model ,@RequestParam("page") int page ,@RequestParam("keyword") String keyword) {
		List<BoardVo> list =boardService.getList(page ,keyword);
		Boolean t_count = boardService.count(keyword);
		int total_count = 0 ;
		model.addAttribute("list",list);
		model.addAttribute("total_count",total_count);
		model.addAttribute("pagenumber", ((page-1)/5)*5);
		model.addAttribute("index",(total_count-((page-1)*5)));
		model.addAttribute("page",page);
		model.addAttribute("keyword",keyword);
		
		return "board/list";
		
	}

}
