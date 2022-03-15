package com.nft.store.service;

import com.nft.store.dao.WDAO;
import com.nft.store.dto.PAGE;
import com.nft.store.dto.WALLET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Service
public class WService {

    @Autowired
    private WDAO wdao;

    private ModelAndView mav = new ModelAndView();

    // 결제
    public String payment(String mId, String price) {

        int pay = wdao.payment(mId, price);

        String result = null;

        if (pay > 0) {
            result = "y";
            wdao.confirm(mId, price);
        }
        return result;
    }

    // 결제내역보기 메소드
    public ModelAndView wList(String wId, int page) {
        PAGE paging = new PAGE();


        List<WALLET> WalletList = wdao.wList(wId);

        mav.addObject("WalletList", WalletList);
        mav.setViewName("W_list");

        return mav;
    }
}
