package com.nft.store.dao;

import com.nft.store.dto.WALLET;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WDAO {
    int payment(String mId, String price);


    void confirm(String mId, String price);

    List<WALLET> wList(String wId);
}
