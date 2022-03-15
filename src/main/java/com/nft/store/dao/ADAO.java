package com.nft.store.dao;

import com.nft.store.dto.AMARKING;
import com.nft.store.dto.APRICE;
import com.nft.store.dto.AUCTION;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ADAO {

    int aSellRegist(AUCTION adto);

    int aRegiView(AUCTION adto);

    AUCTION aView(int acode);

    List<AUCTION> aCodeList(String aCate);

    List<AUCTION> aCodeList2(String aCate);

    int aModify(AUCTION adto);

    int aDelete(int acode);

    int apCount(int apcode);

    void apZero(AUCTION adto);

    int highprice(int apcode);

    void aRankUpdate(int apcode);

    void aZeroDel(int apcode);

    int aBid(APRICE apdto);

    int amCount(AMARKING amdto);

    void amAdd(AMARKING amdto);

    void amDel(AMARKING amdto);

    int amTCount(AMARKING amdto);

    void amUpdate(AUCTION adto);

    List<AUCTION> aSearchList(@Param("akey") String akey, @Param("aCate") String aCate);

    List<AUCTION> aSearchList2(@Param("akey") String akey, @Param("aCate") String aCate);

    List<AUCTION> aSearchList3(@Param("akey") String akey, @Param("aCate") String aCate);

    List<AUCTION> aSearchList4(@Param("akey") String akey, @Param("aCate") String aCate);

    List<AUCTION> aSearchList5(@Param("akey") String akey, @Param("aCate") String aCate);

    List<AUCTION> aSearchList6(@Param("akey") String akey, @Param("aCate") String aCate);

    List<AUCTION> aSearchList7(@Param("akey") String akey, @Param("aCate") String aCate);

    List<AUCTION> aSearchList8(@Param("akey") String akey, @Param("aCate") String aCate);

    List<AUCTION> aSearchList9(@Param("akey") String akey, @Param("aCate") String aCate);

    List<AUCTION> aStSearch0();

    int aStUpdate0(int acode);

    List<AUCTION> aStSearch1();

    int aStUpdate1(int acode);

    String aSearchId(int acode);

    APRICE aPayForm(int acode);

    int aWCount(APRICE apdto);

    void aWPay(APRICE pay);

    void aStUpdate2(int apcode);

}
