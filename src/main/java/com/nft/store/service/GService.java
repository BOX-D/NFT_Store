package com.nft.store.service;

import com.nft.store.dao.GDAO;
import com.nft.store.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class GService {
    @Autowired
    private GDAO dao;

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletResponse response;

    private ModelAndView mav = new ModelAndView();

    // 글 상세보기
    public ModelAndView gView(int gNo) {
        GALLERY gall = dao.gView(gNo);      // 글 상세 정보
        List<COMMENTS> commentsList = dao.gComments(gNo);   // 댓글 리스트
        mav.addObject("gallery", gall);
        mav.addObject("comment", commentsList);
        mav.setViewName("G_view");
        return mav;
    }
    // 프로필 페이지로
    public ModelAndView gProfile(String gWriter) {
        List<GALLERY> popular = dao.popular(gWriter);   // 인기글 top3 목록
        List<GALLERY> gList = dao.pList(gWriter);       // gWriter가 쓴 글 목록
        mav.addObject("pList",popular);
        mav.addObject("gList",gList);
        mav.addObject("gWriter", gWriter);
        mav.setViewName("G_profile");
        return mav;
    }
    // 좋아요 눌렀을때
    public ModelAndView gLike(LIKE like, boolean iLike) {
        if(iLike){
            dao.gDislike(like); // 내가 눌렀었으면 하트 취소
        } else{
            dao.gLike(like);    // 안눌렀었으면 누르기
        }
        mav.addObject("iLike",iLike);
        mav.setViewName("redirect:/gView?gNo="+like.getLNo());
        return mav;
    }
    // 좋아요 내가 눌렀었는지 확인하는 메소드
    public boolean iLike(LIKE like){
        boolean iLike = true;   // 눌렀었으면 true
        String like1 = dao.iLike(like);
        if(like1 == null){
            iLike = false;      // 안눌렀었으면 false
        }
        return iLike;
    }

    // 글 수정 페이지로
    public ModelAndView gModiform(int gNo) {
        GALLERY gall = dao.gView(gNo);
        mav.addObject("gallery", gall);
        mav.setViewName("G_modify");
        return mav;
    }
    // 글 수정
    public ModelAndView gModify(GALLERY gallery) {
        int result = dao.gModify(gallery);
        if(result>0){
            mav.setViewName("redirect:/gView?gNo="+gallery.getGNo());
        }
        return mav;
    }
    // 글 삭제
    public ModelAndView gDelete(int gNo) {
        // DB의 FK 값 때문에 순서대로 삭제
        int result1 = dao.cDelete(gNo);     // 그 글의 댓글 전부 삭제
        int result2 = dao.lDelete(gNo);     // 좋아요 삭제
        int result3 = dao.gDelete(gNo);     // 글 자체 삭제
        if(result3>0){
            mav.setViewName("redirect:/gProfile");
        }
        return mav;
    }
    // 댓글 수정
    public ModelAndView cModify(COMMENTS comment) {
        int result = dao.cModify(comment);
        if(result>0){
            mav.setViewName("redirect:/gView?gNo="+comment.getCgNo());
        }
        return mav;
    }
    // 댓글 삭제
    public ModelAndView cDelete(int cNo, int cgNo) {
        int result = dao.cOneDelete(cNo);    // 댓글 하나만 삭제
        if(result>0){
            mav.setViewName("redirect:/gView?gNo="+cgNo);
        }
        return mav;
    }
    // 댓글 작성
    public ModelAndView cWrite(COMMENTS comment) {
        int result = dao.cWrite(comment);
        if (result > 0) {
            mav.setViewName("redirect:/gView?gNo="+comment.getCgNo());
        }
        return mav;
    }

    // 구매리스트 작품 선택 후 -> 작성페이지(bCode 넘기기)
    public ModelAndView writeForm(int bCode) {

        BLIST blist = dao.writeForm(bCode);
        mav.addObject("blist", blist);
        mav.setViewName("G_Write");
        return mav;
    }

    // 갤러리 작성
    public ModelAndView gWrite(GALLERY gallery) throws IOException {

        int result = dao.gWrite(gallery);

        if (result > 0) {
            mav.setViewName("redirect:/gList");     // 갤러리 메인 리스트로 이동
        } else {
            mav.setViewName("redirect:/writeForm"); // 아닐 경우 작성 폼으로 이동
        }
        return mav;
    }

    // 구매리스트
    public ModelAndView bList(String bId) {
        List<BLIST> buyList = dao.bList(bId);

        // 구매한 작품이 없을 경우 alert 창 띄우고 게시글 작성 막기
        if(!buyList.isEmpty()){
            mav.addObject("buyList", buyList);  // 작성 가능한 경우: 구매리스트로 이동
            mav.setViewName("B_List");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.println("<script>alert('작품구매를 하지 않으면 작성이 불가합니다');</script>");   // alert 창 띄우기
                gList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mav;
    }

    // 갤러리 메인 리스트
    public ModelAndView gList() {

        List<GALLERY> galleryList = dao.gList();
        List<GALLERY> top = dao.top();   // 인기글 top3 목록

        mav.addObject("tList",top);
        mav.addObject("galleryList", galleryList);
        mav.setViewName("G_List");
        return mav;

    }

    // 메인리스트에서 검색기능
    public ModelAndView gSearch(String selectVal, String keyword) {

        List<GALLERY> sList = dao.gSearch(selectVal, keyword);  // 선택 종류, 검색 내용

        mav.addObject("sList", sList);
        mav.setViewName("GS_List"); // 검색 후 나오는 갤러리 리스트 페이지
        return mav;
    }
}
