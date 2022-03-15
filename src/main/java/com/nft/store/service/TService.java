package com.nft.store.service;

import com.nft.store.dao.TDAO;
import com.nft.store.dto.MEMBER;
import com.nft.store.dto.TR_DTO;
import com.nft.store.dto.T_DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class TService {

    @Autowired
    private TDAO tdao;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();




    // 상품 등록 메소드
    public ModelAndView tRegist(T_DTO market) throws IOException  {
        // 1. 파일 불러오기
        MultipartFile TFile = market.getTFILE();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = TFile.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 3번(난수)과 2번(원본파일이름) 합치기!
        String TPRODUCT = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        String savePath = "C:/Boot/store/src/main/resources/static/T_file/"+TPRODUCT;

        // 6. 파일 선택여부
        if(!TFile.isEmpty()){
            market.setTPRODUCT(TPRODUCT);
            TFile.transferTo(new File(savePath));
        }

        // Q. 입력, 수정, 삭제 시 필요한 데이터타입과 변수는?
        int result = tdao.tregist(market);

        // 성공하면 result = 1 , 실패하면 result = 0

        if(result>0){
            mav.setViewName("redirect:/");
        } else {
            mav.setViewName("T_regist");
        }

        return mav;


    }
    // 상품 상세보기 메소드
    public ModelAndView tView(int tcode) {

        T_DTO market = tdao.tView(tcode);

        if(market!=null){
            mav.addObject("market", market);
            mav.setViewName("T_view");
        } else {
            mav.setViewName("redirect:/tList");
        }

        return mav;

    }

    // 상품 등록 글 삭제
    public ModelAndView tDelete(int tcode) {
        int result = tdao.tDelete(tcode);

        if(result>0){
            mav.setViewName("redirect:/tList");
        } else {
            mav.setViewName("redirect:/tView?tcode=" + tcode);
        }

        return mav;

    }

    // 상품 등록 정보 글 수정페이지
    public ModelAndView tModiForm(int tcode) {

        // 상세보기 때 만들어 놓은 tView(tcode)메소드 사용
        T_DTO market = tdao.tView(tcode);

        if(market!=null){
            mav.addObject("market", market);
            mav.setViewName("T_modify");
        } else {
            mav.setViewName("redirect:/tList");
        }

        return mav;

    }

    // tModify: 상품 등록 수정
    public ModelAndView tModify(T_DTO market) throws IOException{

        // 1. 파일 불러오기
        MultipartFile TFile = market.getTFILE();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = TFile.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 3번(난수)과 2번(원본파일이름) 합치기!
        String TPRODUCT = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        String savePath = "C:/Boot/store/src/main/resources/static/T_file/"+TPRODUCT;

        // 6. 파일 선택여부
        if(!TFile.isEmpty()){
            market.setTPRODUCT(TPRODUCT);
            TFile.transferTo(new File(savePath));
        }

        //  System.out.println("Service" + market);

        // Q. 입력, 수정, 삭제 시 필요한 데이터타입과 변수는?
        int result = tdao.tModify(market);

        // 성공하면 result = 1 , 실패하면 result = 0

        if(result>0){
            mav.setViewName("redirect:/tView?TCODE="+market.getTCODE());
        } else {
            mav.setViewName("redirect:/tModiForm?TCODE="+market.getTCODE());
        }

        return mav;
    }


    // 상품 등록 photo 목록 보기 메소드
    public ModelAndView tList(String TCATEGORY, int filter) {

        List<T_DTO> marketList =  null;

        marketList = tdao.tDateASC(TCATEGORY);  // 해당 상품에 대한 모든 정보를 marketlist에 담는다.


        mav.addObject("tList", marketList);

        if(TCATEGORY.equals("photo")){
            mav.setViewName("T_photolist");

        } else if(TCATEGORY.equals("illust")){
            mav.setViewName("T_illustlist");

        } else {
            mav.setViewName("index");
        }

        return mav;

    }




    // 상품 좋아요
    public int tLike(T_DTO market) {
        tdao.h0Add(market);
        int Searchlike = tdao.hSearch(market);      // SeachLike(TMLIKE) 값은 1 아니면 0

        if(Searchlike == 1){   // 이전에 하트를 누른 상태 일때
            tdao.hDelete(market);       // 상품에 관한 하트 취소

        } else  {    // 이전에 하트를 누른 적이 없는 상태 일때
            tdao.hDelete(market);
            tdao.hAdd(market);          // 상품에 관한 하트 추가
        }

        int result = tdao.tLike(market); // result = 하트 누른 아이디 수
        System.out.println(result);
        market.setTSUMLIKE(result);
        System.out.println(market.getTSUMLIKE());
        System.out.println(market);
        tdao.tsumLike(market);



        return result;

    }

    // 상품 구매 페이지로 이동
    public ModelAndView tBuyForm(int tcode) {

        // 상세보기 때 만들어 놓은 tView(tcode)메소드 사용
        T_DTO market = tdao.tView(tcode);

        if(market!=null){
            mav.addObject("market", market);
            mav.setViewName("T_buy");
        } else {
            mav.setViewName("redirect:/tList");
        }


        return mav;
    }


    // 상품 구매아이디 중복 확인
    public String T_buyidcheck(String trid, int trcode) {
        TR_DTO trecode = new TR_DTO();
        trecode.setTRID(trid);
        trecode.setTRCODE(trcode);

        String buyidcheck = tdao.tBuyidcheck(trecode);
        String result = null;

        if(buyidcheck==null) {  // 장부에 구매한 이력이 없다면
            result = "buy";     // 구매 가능한 상태

        } else {
            result = "notbuy";  // 구매 불가능!
        }

        return result;
    }

    // 상품 재고 확인
    public int T_numbercheck(int trcode) {

        int Searchnum = tdao.tsnum(trcode);   // 거래된 상품 개수

        T_DTO market  = tdao.tView(trcode);
        int Tcount = market.getTNUM();     // 판매가능 상품 개수
        int Tsave = Tcount - Searchnum;

        // 재고(Tcount - Searchnum) 확인
        int result;

        if( Tcount - Searchnum <=0){        // 재고가 없음!
            tdao.tbuydone(trcode);
            result = 0;
        } else{                 // 재고가 있음!
            result = Tsave;
        }

        return result;

    }

    // 상품 구매
    public ModelAndView tBuy(TR_DTO trecode) {
        Timestamp TRDATE = new Timestamp(System.currentTimeMillis());
        trecode.setTRDATE(TRDATE);

        // 장부 기록
        int result = tdao.tbuyadd(trecode);
        int Searchnum = tdao.tsnum(trecode.getTRCODE());   // 거래된 상품 개수

        T_DTO market  = tdao.tView(trecode.getTRCODE());
        int Tcount = market.getTNUM();     // 등록한 판매 가능 상품 개수
        int Tsave = Tcount - Searchnum; // 현재 재고

        // 구매 목록 기록
        market.setTMAKER((String) session.getAttribute("loginId"));
        System.out.println(market);
        tdao.buyList(market);

        if(Tsave==0){
            tdao.tbuydone(trecode.getTRCODE());
        }

        //결제
        tdao.tpay(trecode);

        // 성공하면 result = 1 , 실패하면 result = 0

        if(result>0){
            mav.setViewName("redirect:/");
        } else {
            mav.setViewName("T_buy");
        }


        return mav;
    }


    // photo 키워드 검색 정렬
    public ModelAndView tSearch(String TCATEGORY, int filter, String TKEY) {

        List<T_DTO> tSearchList = null;

        if (filter ==1){
            tSearchList = tdao.tkDateASC(TKEY,TCATEGORY); // photo 최신순(상품번호 높은순) 정렬
        } else if(filter ==2){
            tSearchList = tdao.tkDateDESC(TKEY,TCATEGORY); // photo 오래된순(상품번호 낮은순) 정렬
        } else if(filter ==3){
            tSearchList = tdao.tkHeartASC(TKEY,TCATEGORY); // photo 하트 높은 순 정렬
        } else if(filter ==4){
            tSearchList = tdao.tkHeartDESC(TKEY,TCATEGORY); // photo 하트 낮은 순 정렬
        } else if(filter ==5){
            tSearchList = tdao.tkPriceASC(TKEY,TCATEGORY); // photo 높은 가격 순 정렬
        } else if(filter ==6) {
            tSearchList = tdao.tkPriceDESC(TKEY,TCATEGORY); // photo 낮은 가격 순 정렬
        } else {
            tSearchList = tdao.tDone(TKEY, TCATEGORY);
        }


        mav.addObject("tList",tSearchList);

        if(TCATEGORY.equals("photo")){
            mav.setViewName("T_photolist");

        } else if(TCATEGORY.equals("illust")){
            mav.setViewName("T_illustlist");
        }

        return mav;

    }


    // 잔액 조회
    public int tpay(TR_DTO trecode) {
        int result = tdao.twallet(trecode);     // 잔액 확인
        T_DTO market = tdao.tView(trecode.getTRCODE()); // 상품 정보 가져오기

        int Tprice = market.getTPRICE();    // 상품가격

        if(result >= Tprice){       // 구매가능
            result = 1;
        } else {                    // 잔액 부족
            result = -1;
        }

        return result;
    }
}
