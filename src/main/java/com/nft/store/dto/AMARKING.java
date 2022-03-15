package com.nft.store.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("amarking")
public class AMARKING {
    int AMCODE;
    String AMID;
    int AMLIKE;
}
