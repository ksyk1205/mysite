package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	@RequestMapping(value={"/list",""})
	public String list(Model model ,
			@RequestParam(value="page", defaultValue = "1", required = false) int page ,
			@RequestParam(value="keyword",defaultValue = "", required = false) String keyword){
		
		List<BoardVo> list =boardService.getList(page ,keyword);
		
		int total_count = boardService.count(keyword);
		model.addAttribute("list",list);
		model.addAttribute("total_count",total_count);		
		model.addAttribute("pagenumber", ((page-1)/5)*5);
		model.addAttribute("index",(total_count-((page-1)*5)));
		model.addAttribute("page",page);
		model.addAttribute("keyword",keyword);
		
		return "board/list";
	}
	
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no, Model model,
			@RequestParam(value="page", defaultValue = "1", required = false) int page) {	
		model.addAttribute("vo",boardService.view(no));
		model.addAttribute("page",page);
		boardService.hit(no);
		return "board/view";
	} 
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session) {
		if(session !=null && session.getAttribute("authUser")!=null) {
			return "/board/write";
		}
		return "redirect:/board";		
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String add(@ModelAttribute BoardVo vo,
			HttpSession session , @RequestParam(value="page", defaultValue = "1", required = false)int page) {
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		vo.setUser_no(authUser.getNo());
		if(vo.getG_no() ==  null|| vo.getO_no() == null || vo.getDepth()==null) {
			boardService.write(vo);
		}else {
			boardService.updateinsert(vo);
			boardService.newinsert(vo);
		}
		return "redirect:/board/list?page="+page;
	}
	//답글 쓰기위하여 vo를 가지고 넘겨줌
	@RequestMapping(value="/request/{no}", method=RequestMethod.GET)
	public String request(@PathVariable("no") Long no,@RequestParam(value="page", defaultValue = "1", required = false) int page,Model model) {
		
		BoardVo vo = boardService.view(no);
		model.addAttribute("vo",vo);
		return "/board/write";
	}
	
	@RequestMapping(value="/modify/{no}",method=RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, 
			@RequestParam(value="page", defaultValue = "1", required = false) int page,Model model,
			HttpSession session) {
		
		if(session ==null) {
			return "redirect:/board";
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser ==null) {
			return "redirect:/board";
		}

		BoardVo vo = boardService.view(no);
		model.addAttribute("vo",vo);
		
		if(authUser.getNo()!=vo.getUser_no()) {
			return "redirect:/board";
		}
		return "board/modify";
	}
	
	@RequestMapping(value="/modify/{no}",method=RequestMethod.POST)
	public String modify(@PathVariable("no") Long no, 
			@RequestParam(value="page", defaultValue = "1", required = false) int page,
			@ModelAttribute BoardVo vo) {
		boardService.modify(vo);
		return "redirect:/board/view/"+no;
	}
	
	@RequestMapping(value="/delete/{no}",method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no,HttpSession session, Model model,
			@RequestParam(value="page", defaultValue = "1", required = false)int page) {
		if(session==null) {
			return "redirect:/board";
		}
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		model.addAttribute("authUser",authUser);
		if(authUser ==null) {
			return "redirect:/board";
		}
		BoardVo vo = boardService.view(no);
		if(authUser.getNo()!=vo.getUser_no()) {
			return "redirect:/board";
		}
		
		boardService.delete(no);
		return "redirect:/board/list?page="+page;
	}
}
