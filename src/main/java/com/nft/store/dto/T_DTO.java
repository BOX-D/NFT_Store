package com.nft.store.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("market")
public class T_DTO {
      int TCODE;              // 상품코드
      String TCATEGORY;       // 카테고리
      String TNAME;           // 상품 이름
      String TMAKER;          // 작가
      MultipartFile TFILE;    // 업로드 파일/사진
      String TPRODUCT;        // 업로드 파일/사진 이름
      String TDETAIL;         // 상품설명
      int TNUM;               // 상품 개수
      int TPRICE;             // 상품 가격
      String TDATE;           // 업로드 날짜
      String TKEY;            // 키워드
      int TSUMLIKE;           // 하트 수
      int TSTATE;             // 상품 상태
      String MPROFILENAME;    // 멤버 프로필사진


      int TMCODE;             // TM 상품코드
      String TMID;            // TM 멤버아이디
      int TMLIKE;             // TM 하트



}
