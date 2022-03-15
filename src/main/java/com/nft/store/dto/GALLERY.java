package com.nft.store.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("gallery")
public class GALLERY {
    int gNo;        // 번호
    String gWriter; // 작성자
    String gDate;   //날짜
    String gName;   // 작품명
    String gMaker;  // 작가
    String gContent;//내용
    String gKey;    //키워드
    String gFileName;//파일
    String LikeCount;// 좋아요 갯수
    String gProfile; // 작성자 프로필사진
}
