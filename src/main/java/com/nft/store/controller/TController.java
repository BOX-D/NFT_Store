package com.nft.store.controller;

import com.nft.store.dto.TR_DTO;
import com.nft.store.dto.T_DTO;
import com.nft.store.service.TService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


@Controller
public class TController {

    @Autowired
    private TService tsvc;

    private ModelAndView mav = new ModelAndView();

    // tRegistForm: 상품 등록 페이지로 이동
    @RequestMapping(value = "/tRegistForm", method = RequestMethod.GET)
    public String registForm() {
        return "T_regist";
    }

    // tRegist : 상품 등록
    @RequestMapping(value = "/tRegist", method = RequestMethod.POST)
    public ModelAndView tRegist(@ModelAttribute T_DTO market) throws IOException {
        mav = tsvc.tRegist(market);
        return mav;
    }

    // tView: 상품 등록 정보 상세보기
    @RequestMapping(value = "/tView", method = RequestMethod.GET)
    public ModelAndView tView(@RequestParam("TCODE") int TCODE) {
        mav = tsvc.tView(TCODE);
        return mav;
    }

    // tModiForm : 상품 등록 게시글 수정페이지로 이동
    @RequestMapping(value = "/tModiForm", method = RequestMethod.GET)
    public ModelAndView tModiForm(@RequestParam("TCODE") int TCODE) {
        mav = tsvc.tModiForm(TCODE);
        return mav;
    }

    // tModify : 상품 등록 수정
    @RequestMapping(value = "/tModify", method = RequestMethod.POST)
    public ModelAndView tModify(@ModelAttribute T_DTO market) throws IOException {
        // System.out.println("Controller" + market);
        mav = tsvc.tModify(market);
        return mav;
    }


    // tDelete: 상품 등록 글 삭제
    @RequestMapping(value = "/tDelete", method = RequestMethod.GET)
    public ModelAndView tDelete(@RequestParam("TCODE") int TCODE) {
        mav = tsvc.tDelete(TCODE);
        return mav;
    }


    // tList : 상품  목록
    @RequestMapping(value = "/tList", method = RequestMethod.GET)
    public ModelAndView tList(@RequestParam(value = "TCATEGORY", required = false, defaultValue = "photo") String TCATEGORY
            , @RequestParam(value = "filter", required = false, defaultValue = "1") int filter) {
        mav = tsvc.tList(TCATEGORY, filter);
        return mav;
    }


    // T_Heartup: 상품 하트
    @RequestMapping(value = "/T_Heartup", method = RequestMethod.POST)
    public @ResponseBody
    int tLike(@ModelAttribute T_DTO market) {
        int result = tsvc.tLike(market);
        return result;
    }

    // T_buyidcheck : 상품 중복 구매 방지 아이디 체크
    @RequestMapping(value = "/T_buyidcheck", method = RequestMethod.POST)
    public @ResponseBody
    String T_buyidcheck(@RequestParam("TRID") String TRID, @RequestParam("TRCODE") int TRCODE) {
        String result = tsvc.T_buyidcheck(TRID, TRCODE);
        return result;
    }

    // T_numbercheck : 상품 재고 확인
    @RequestMapping(value = "/T_numbercheck", method = RequestMethod.POST)
    public @ResponseBody int T_numbercheck(@RequestParam("TRCODE") int TRCODE) {
        int result = tsvc.T_numbercheck(TRCODE);
        return result;
    }


    // tBuyForm: 상품 구매 페이지로 이동
    @RequestMapping(value = "/tBuyForm", method = RequestMethod.GET)
    public ModelAndView tBuyForm(@RequestParam("TCODE") int TCODE) {
        mav = tsvc.tBuyForm(TCODE);
        return mav;
    }

    // tBuy : 상품 구매
    @RequestMapping(value = "/tBuy", method = RequestMethod.POST)
    public ModelAndView tBuy(@ModelAttribute TR_DTO trecode) {
        mav = tsvc.tBuy(trecode);
        return mav;
    }

    // tSearch:   키워드 검색
    @RequestMapping(value = "/tSearch", method = RequestMethod.GET)
    public ModelAndView tSearch(@RequestParam(value = "TCATEGORY", required = false, defaultValue = "photo") String TCATEGORY
            , @RequestParam(value = "filter", required = false, defaultValue = "1") int filter,
                                @RequestParam(value = "TKEY", required = false, defaultValue = "") String TKEY) {
        System.out.println("TKEY : " + TKEY);

        mav = tsvc.tSearch(TCATEGORY, filter,TKEY);
        return mav;
    }

    // tpay : 잔액 조회
    @RequestMapping(value = "/tpay", method = RequestMethod.POST)
    public @ResponseBody int tpay(@ModelAttribute TR_DTO trecode) {
        int result = tsvc.tpay(trecode);
        return result;
    }



}
