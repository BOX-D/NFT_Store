package com.nft.store.dao;

import com.nft.store.dto.TR_DTO;
import com.nft.store.dto.T_DTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TDAO {



    int tregist(T_DTO market);

    T_DTO tView(int tcode);

    int tDelete(int tcode);

    int tModify(T_DTO market);

    int hSearch(T_DTO market);


    void hDelete(T_DTO market);

    void hAdd(T_DTO market);

    int tLike(T_DTO market);

    void h0Add(T_DTO market);


    String tBuyidcheck(TR_DTO trecode);

    int tsnum(int trecode);

    int tbuyadd(TR_DTO trecode);

    List<T_DTO> tDateASC(String tcategory);

    void tsumLike(T_DTO market);

    List<T_DTO> tkDateASC(@Param("TKEY") String TKEY, @Param("TCATEGORY") String TCATEGORY);

    List<T_DTO> tkDateDESC(@Param("TKEY") String TKEY, @Param("TCATEGORY") String TCATEGORY);

    List<T_DTO> tkHeartASC(@Param("TKEY") String TKEY, @Param("TCATEGORY") String TCATEGORY);

    List<T_DTO> tkHeartDESC(@Param("TKEY") String TKEY, @Param("TCATEGORY") String TCATEGORY);

    List<T_DTO> tkPriceASC(@Param("TKEY") String TKEY, @Param("TCATEGORY") String TCATEGORY);

    List<T_DTO> tkPriceDESC(@Param("TKEY") String TKEY, @Param("TCATEGORY") String TCATEGORY);

    List<T_DTO> tDone(@Param("TKEY") String TKEY, @Param("TCATEGORY") String TCATEGORY);

    void tbuydone(int trcode);

    int twallet(TR_DTO trecode);

    void tpay(TR_DTO trecode);

    void buyList(T_DTO market);
}
