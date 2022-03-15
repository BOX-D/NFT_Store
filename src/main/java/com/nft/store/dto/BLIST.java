package com.nft.store.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("blist")
public class BLIST {
    int bCode;
    String bId;
    String bName;
    String bProduct;
}
