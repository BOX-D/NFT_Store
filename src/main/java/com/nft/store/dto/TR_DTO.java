package com.nft.store.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@Alias("trecode")
public class TR_DTO {
    int TRCODE;       // TR 상품코드
    String TRID;         // TR 구매자 아이디
    int TRPRICE;      // TR 가격
    Timestamp TRDATE;         // TR 구매날짜
}
