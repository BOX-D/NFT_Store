package com.nft.store.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("wallet")
public class WALLET {
    String wId;
    int wPrice;
    String wDate;
}
