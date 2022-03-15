package com.nft.store.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("aprice")
public class APRICE {
    int APCODE;
    String APID;
    int APPRICE;
    int APRANK;

    String MNICK;
}
