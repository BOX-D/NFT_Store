package com.nft.store.controller;


import com.nft.store.service.WService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WController {
    @Autowired
    private WService wsvc;

    private ModelAndView mav = new ModelAndView();

    // W_payment : 결제정보 등록
    @RequestMapping(value = "W_payment", method = RequestMethod.GET)
    public @ResponseBody
    String payment(@RequestParam("mId") String mId, @RequestParam("price") String price) {
        System.out.println("mId : " + mId + " price :" + price);
        String result = wsvc.payment(mId, price);

        return result;
    }

    // W_list : 결제목록 보기
    @RequestMapping(value = "W_list", method = RequestMethod.GET)
    public ModelAndView wList(@RequestParam("wId") String wId,@RequestParam(value="page", required= false, defaultValue="1") int page) {

        mav = wsvc.wList(wId,page);

        return mav;
    }
}
