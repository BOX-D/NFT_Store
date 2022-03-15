package com.nft.store.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("paging")
public class PAGE {
    private int page;
    private int maxPage;
    private int startPage;
    private int endPage;
    private int startRow;
    private int endRow;
}
