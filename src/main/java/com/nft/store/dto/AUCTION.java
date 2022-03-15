package com.nft.store.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Data
@Alias("auction")
public class AUCTION {
    int ACODE;
    String ACATEGORY;
    String ANAME;
    String AMAKER;
    MultipartFile AFILE;
    String APRODUCT;
    String ADETAIL;
    int APRICE;
    String AKEY;
    int AMTOTAL;
    int ASTATE;

    String ASTART1;
    String AEND1;

    Timestamp ASTART;
    Timestamp AEND;

    String MPROFILENAME;

}
