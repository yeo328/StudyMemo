package com.yeo.studywebmemo.controllers;

import com.yeo.studywebmemo.entities.MemoEntity;
import com.yeo.studywebmemo.models.PagingModel;
import com.yeo.studywebmemo.services.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class HomeController {
    //http://localhost:port/{컨트롤러}/{메서드} //홈컨트롤러는 컨트롤러없이 바로 메서드
    //http://localhost:port/{종류}/{행동}

    private final MemoService memoService;
    @Autowired
    public HomeController(MemoService memoService) {
        this.memoService = memoService;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET) //첫화면 나타내는것
    public ModelAndView getIndex(@RequestParam(value="p", defaultValue = "1", required = false)int requestPage,
                                 @RequestParam(value = "c", defaultValue = "content", required = false)String
                                 searchCriterion,
                                 @RequestParam(value = "q", defaultValue = "", required = false)String searchQuery){
        ModelAndView modelAndView = new ModelAndView("home/index"); //템플릿 경로 Command+클릭하면 경로로 가는지 확인
        PagingModel pagingModel = new PagingModel(
                MemoService.PAGE_COUNT,
                this.memoService.getCount(searchCriterion, searchQuery),
                requestPage);
        MemoEntity[] memoEntities = this.memoService.getByPage(pagingModel, searchCriterion,searchQuery);
        modelAndView.addObject("memos",memoEntities);
        modelAndView.addObject("pagingModel", pagingModel);
        modelAndView.addObject("searchCriterion",searchCriterion);
        modelAndView.addObject("searchQuery",searchQuery);
        return modelAndView;
    }
    @RequestMapping(value = "/",method = RequestMethod.POST)
    //@PostMapping("/")
    public ModelAndView postIndex(@RequestParam(value="p", defaultValue = "1", required = false)int requestPage,
                                  @RequestParam(value = "c", defaultValue = "content", required = false)String
                                          searchCriterion,
                                  @RequestParam(value = "q", defaultValue = "", required = false)String searchQuery,
                                  MemoEntity memoEntity){
        boolean result = this.memoService.write(memoEntity);
        ModelAndView modelAndView = this.getIndex(requestPage, searchCriterion, searchQuery);
        modelAndView.addObject("result", result);
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseBody //이걸 붙이면 "true" 가 응답으로 감
    public String deleteIndex(@RequestParam(value="index") int index){
        boolean result = this.memoService.deleteByIndex(index);
        return String.valueOf(result);
    }
    @RequestMapping(value = "/", method = RequestMethod.PATCH)
    @ResponseBody //이걸 붙이면 "true" 가 응답으로 감

    public String updateIndex(@RequestParam(value = "index")int index,
                              @RequestParam(value = "text") String text){
        boolean result = this.memoService.updateByIndex(index,text);
        return String.valueOf(result);
    }
}
//http://localhost:6795/?p=2 or p=3 ...페이지이

//첫화면은 get
//보내줄때는 post