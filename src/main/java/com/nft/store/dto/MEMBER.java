package com.nft.store.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Alias("member")
@Data
public class MEMBER {
    String mId;
    String mPw;
    String mName;
    String mNick;
    String mPhone;
    String mBirth;
    int mWallet;
    int mState;
    MultipartFile mProfile;
    String mProfileName;

    String yy;
    String mm;
    String dd;
}
