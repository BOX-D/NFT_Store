package com.nft.store.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("like")
public class LIKE {
    int lNo;
    String lId;
}
