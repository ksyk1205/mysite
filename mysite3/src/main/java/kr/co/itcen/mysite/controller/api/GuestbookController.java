package kr.co.itcen.mysite.controller.api;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.itcen.mysite.dto.JSONResult;
import kr.co.itcen.mysite.service.GuestbookService;
import kr.co.itcen.mysite.vo.GuestbookVo;

@RestController("guestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	//@ResponseBody RestController를 사용하면 ResponseBody를 사용하지않아도된다.
	@PostMapping(value="/add")
	public JSONResult add(@RequestBody GuestbookVo vo) {
		guestbookService.insert(vo);
		System.out.println(vo);
		return JSONResult.success(vo);
		
	}
	//@ResponseBody
	@GetMapping(value="/list/{no}")
	public JSONResult list(@PathVariable("no") Long startNo) {
		List<GuestbookVo> list= guestbookService.getList(startNo);
		return JSONResult.success(list);
	}
	@DeleteMapping(value="/{no}")
	public JSONResult delete(@PathVariable("no") Long no,
			@RequestParam("password") String password) {
		boolean result= guestbookService.delete(no, password);
		
		return JSONResult.success(result ? no : -1);
	}
}
