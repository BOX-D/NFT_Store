package com.nft.store.dao;

import com.nft.store.dto.AUCTION;
import com.nft.store.dto.MEMBER;
import com.nft.store.dto.PAGE;
import com.nft.store.dto.T_DTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MDAO {
    int mJoin(MEMBER MDTO);

    MEMBER mLogin(MEMBER MDTO);

    List<MEMBER> mList(PAGE paging);

    int mModify(MEMBER MDTO);

    int mDelete(String mId);

    MEMBER mView(String mId);

    String idoverlap(String mId);

    int mState(String mId);

    String Nickoverlap(String mNick);


    int mListCount();

    String mFindId(String mName, String mBirth);

    String mFindPw(String mId, String mName, String mBirth);

    void ChangePw(String mId, String mPw);

    List<AUCTION> aList();

    List<T_DTO> tList();
}
