package com.nft.store.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("comment")
public class COMMENTS {
    int cgNo;
    int cNo;
    String cWriter;
    Date cDate;
    String cContent;
    String cProfile;
}
