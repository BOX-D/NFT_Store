package com.nft.store.dao;


import com.nft.store.dto.BLIST;
import com.nft.store.dto.COMMENTS;
import com.nft.store.dto.GALLERY;
import com.nft.store.dto.LIKE;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GDAO {
    // 글 상세정보
    GALLERY gView(int gNo);

    // 좋아요 눌렀었나 확인
    String iLike(LIKE like);

    // 댓글 목록
    List<COMMENTS> gComments(int gNo);

    // 프로필 페이지 글 목록
    List<GALLERY> pList(String gWriter);

    // 좋아요 취소
    void gDislike(LIKE like);

    // 좋아요 추가
    void gLike(LIKE like);

    // 글 수정
    int gModify(GALLERY gallery);

    // 글 삭제
    int gDelete(int gNo);

    // 댓글 삭제
    int cDelete(int gNo);

    // 좋아요 삭제
    int lDelete(int gNo);

    // 댓글 수정
    int cModify(COMMENTS comment);

    // 댓글 삭제
    int cOneDelete(int cNo);

    // 댓글 작성
    int cWrite(COMMENTS comment);

    // 프로필 페이지 인기글 top3
    List<GALLERY> popular(String gWriter);

    // 구매리스트 작품 선택 후 -> 작성페이지
    BLIST writeForm(int bCode);

    // 갤러리 작성
    int gWrite(GALLERY gallery);

    // 구매 목록
    List<BLIST> bList(String bId);

    // 메인 목록
    List<GALLERY> gList();

    // 검색
    List<GALLERY> gSearch(String selectVal, String keyword);

    // 갤러리 인기글 3개
    List<GALLERY> top();
}
