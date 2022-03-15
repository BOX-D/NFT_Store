package com.nft.store.controller;

import com.nft.store.dto.AMARKING;
import com.nft.store.dto.APRICE;
import com.nft.store.dto.AUCTION;
import com.nft.store.service.AService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class AController {

    @Autowired
    private AService asvc;

    private ModelAndView mav = new ModelAndView();

    @Autowired
    private HttpSession session;

    // 경매 등록 페이지로 이동
    @RequestMapping(value = "aRegistForm", method = RequestMethod.GET)
    public ModelAndView aRegistForm(@RequestParam(value = "loginId") String loginId) {
        mav = asvc.aRegistForm(loginId);
        return mav;
    }

    // 경매 시간 비교 ( 경매 마감 시간 - 경매 시작 시간 > 0 )
    @RequestMapping(value = "/timeCheck", method = RequestMethod.POST)
    public @ResponseBody
    String timeCheck(@RequestParam(value = "starttime") String starttime, @RequestParam(value = "endtime") String endtime) {
        String result = asvc.timeCheck(starttime, endtime);
        return result;
    }

    // 경매 등록
    @RequestMapping(value = "aSellRegist", method = RequestMethod.POST)
    public ModelAndView aSellRegist(@ModelAttribute AUCTION adto) throws IOException {
        mav = asvc.aSellRegist(adto);
        return mav;
    }

    // aList : 경매 목록
    @RequestMapping(value = "aList", method = RequestMethod.GET)
    public ModelAndView aList(@RequestParam(value = "aCate", required = false, defaultValue = "photo") String aCate,
                              @RequestParam(value = "filter", required = false, defaultValue = "1") int filter) {
        mav = asvc.aList(aCate, filter);
        return mav;
    }

    // aView : 경매 보기
    @RequestMapping(value = "aView", method = RequestMethod.GET)
    public ModelAndView aView(@ModelAttribute AUCTION adto) {
        mav = asvc.aView(adto);
        return mav;
    }

    // aModiForm : 경매 수정 페이지로 이동
    @RequestMapping(value = "aModiForm", method = RequestMethod.GET)
    public ModelAndView aModiForm(@RequestParam(value = "ACODE") int ACODE) {
        mav = asvc.aModiForm(ACODE);
        return mav;
    }

    // aModify : 경매 수정
    @RequestMapping(value = "aModify", method = RequestMethod.POST)
    public ModelAndView aModify(@ModelAttribute AUCTION adto) {
        mav = asvc.aModify(adto);
        return mav;
    }

    // aDelete : 경매 삭제
    @RequestMapping(value = "aDelete", method = RequestMethod.POST)
    public @ResponseBody
    int aDelete(@RequestParam(value = "ACODE") int ACODE) {
        int result = asvc.aDelete(ACODE);
        return result;
    }

    // aBidForm : 경매 입찰 페이지로 이동
    @RequestMapping(value = "aBidForm", method = RequestMethod.GET)
    public ModelAndView aBidForm(@RequestParam(value = "ACODE") int ACODE) {
        mav = asvc.aBidForm(ACODE);
        return mav;
    }

    // highPrice : 최고가 갱신
    @RequestMapping(value = "highPrice", method = RequestMethod.POST)
    public @ResponseBody
    int highPrice(@RequestParam(value = "APCODE") int APCODE) {
        int price = asvc.highPrice(APCODE);
        return price;
    }

    // aBid : 입찰
    @RequestMapping(value = "aBid", method = RequestMethod.POST)
    public @ResponseBody
    String aBid(@ModelAttribute APRICE apdto) {
        String aBidMent = asvc.aBid(apdto);
        return aBidMent;
    }

    // aMarking : 좋아요
    @RequestMapping(value = "aMarking", method = RequestMethod.POST)
    public @ResponseBody
    int aMarking(@ModelAttribute AMARKING amdto) {
        int AMTotal = asvc.aMarking(amdto);
        return AMTotal;
    }

    // tSearch: 키워드 검색
    @RequestMapping(value = "/aSearch", method = RequestMethod.GET)
    public ModelAndView aSearch(@RequestParam(value = "filter", required = false, defaultValue = "1")int filter,
                                @RequestParam(value = "AKEY" ,required = false,defaultValue = "") String AKEY,
                                @RequestParam(value = "aCate", required = false, defaultValue = "photo") String aCate) {
        mav = asvc.aSearch(filter,AKEY,aCate);
        return mav;
    }

    // 1분 마다 실행
    @Scheduled( cron = "0 0/1 * * * *")
    public void run(){
        int stCount = asvc.aStUpdate();
    }

    // aPayForm : 경매 결제 페이지로 이동
    @RequestMapping(value = "aPayForm", method = RequestMethod.GET)
    public ModelAndView aPayForm(@RequestParam(value = "ACODE") int ACODE) {
        mav = asvc.aPayForm(ACODE);
        return mav;
    }

    // aPay : 결제
    @RequestMapping(value = "aPay", method = RequestMethod.POST)
    public @ResponseBody
    int aPay(@ModelAttribute APRICE apdto) {
        System.out.println("컨트롤러");
        int wallet = asvc.aPay(apdto);
        return wallet;
    }

}
