package com.nft.store.service;

import com.nft.store.dao.ADAO;
import com.nft.store.dto.AMARKING;
import com.nft.store.dto.APRICE;
import com.nft.store.dto.AUCTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class AService {
    @Autowired
    private ADAO adao;

    private ModelAndView mav = new ModelAndView();

    @Autowired
    private JavaMailSender mailSender;

    // 물품 등록 페이지로 이동
    public ModelAndView aRegistForm(String loginId) {
        mav.setViewName("A_RegistForm");
        return mav;
    }

    // 물품 등록
    public ModelAndView aSellRegist(AUCTION adto) throws IOException {
        // 파일 저장
        MultipartFile A_file = adto.getAFILE();
        String AFileName = A_file.getOriginalFilename();
        String AMAKER = adto.getAMAKER();

        // 난수 생성
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 난수_작가이름_파일명 으로 저장
        String APRODUCT = uuid+"_"+AMAKER + "_" + AFileName;

        String savePath = "C:/Boot/store/src/main/resources/static/A_file/" + APRODUCT;

        if (!A_file.isEmpty()) {
            adto.setAPRODUCT(APRODUCT);
            A_file.transferTo(new File(savePath));
        }

        // DB에 저장 하기 위해 String 타입 -> Timestamp(java.sql.Date) 타입으로 변환
        adto.setASTART(java.sql.Timestamp.valueOf(TimeChange(adto.getASTART1())));
        adto.setAEND(java.sql.Timestamp.valueOf(TimeChange(adto.getAEND1())));

        int result = adao.aSellRegist(adto);
        int ACODE = adao.aRegiView(adto);

        if (result > 0) {
            mav.setViewName("redirect:/aView?ACODE=" + ACODE );
        } else {
            mav.setViewName("redirect:/");
        }

        return mav;
    }

    private String TimeChange(String time) {
        // 시간 변경 메소드
        String date = time;
        String date1 = date.substring(0, 10);
        String time1 = date.substring(11, 16);

        date = date1 + " " + time1 + ":00.0";

        return date;
    }


    public String timeCheck(String starttime, String endtime) {
        // starttime < endtime 시작시간이 끝나는 시간보다 작아야된다.

        String result = null;
        int compare = endtime.compareTo(starttime);
        // 비교하는 함수
        if (compare > 0) {
            result = "OK";
        } else if (compare <= 0) {
            result = "NO";
        }
        System.out.println(result);
        return result;
    }

    public ModelAndView aView(AUCTION adto) {
        AUCTION aView = adao.aView(adto.getACODE());
        // APRICE 쪽 테이블 오류 개선 ( 입찰한 기록이없는(새로만들어진) 경매는 Rank 0 추가)
        int apcount = adao.apCount(aView.getACODE());
        if (apcount == 0) {
            adao.apZero(aView);
        }
        String aPayId = adao.aSearchId(adto.getACODE());
        if (aView != null) {
            mav.addObject("aPayId", aPayId);
            mav.addObject("aView", aView);
            mav.setViewName("A_View");
        } else {
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    public ModelAndView aList(String aCate, int filter) {
        List<AUCTION> aList = null;
        if (filter == 1) {
            aList = adao.aCodeList(aCate); // 코드 정순 (과거순)
        } else if (filter == 2) {
            aList = adao.aCodeList2(aCate); // 코드 역순 (최신순)
        }
        mav.addObject("aList", aList);

        if (aCate.equals("photo")) {
            mav.setViewName("A_PhotoList");
        } else if (aCate.equals("illust")) {
            mav.setViewName("A_illustList");
        } else {
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    public ModelAndView aModiForm(int acode) {
        AUCTION aModi = adao.aView(acode);
        if (aModi != null) {
            mav.addObject("aModi", aModi);
            mav.setViewName("A_Modify");
        } else {
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    public ModelAndView aModify(AUCTION adto) {
        int result = adao.aModify(adto);
        AUCTION auc = adao.aView(adto.getACODE());
        if (result > 0) {
            mav.addObject("aView", auc);
            mav.setViewName("A_View");
        } else {
            mav.addObject("aView", auc);
            mav.setViewName("A_View");
        }
        return mav;
    }

    public int aDelete(int acode) {
        int result = adao.aDelete(acode);
        if (result > 0) {
            return result;
        } else {
            result = 0;
        }
        return result;
    }

    public ModelAndView aBidForm(int acode) {
        AUCTION auc = adao.aView(acode);
        if (auc != null) {
            mav.addObject("aBid", auc);
            mav.setViewName("A_Bid");
        } else {
            mav.addObject("aView", auc);
            mav.setViewName("A_View");
        }
        return mav;
    }

    public int highPrice(int APCODE) {
        int price = adao.highprice(APCODE);
        return price;
    }

    public String aBid(APRICE apdto) {
        String aBidMent = null;
        adao.aRankUpdate(apdto.getAPCODE());
        adao.aZeroDel(apdto.getAPCODE());
        int result = adao.aBid(apdto);
        if (result > 0) {
            aBidMent = "OK";
        } else {
            aBidMent = "NO";
        }
        return aBidMent;
    }

    public int aMarking(AMARKING amdto) {
        int AMTotal = 0;
        int AMCount = adao.amCount(amdto);
        if (AMCount == 0) {
            adao.amAdd(amdto);
        } else {
            adao.amDel(amdto);
        }
        AMTotal = adao.amTCount(amdto);
        AUCTION adto = new AUCTION();
        adto.setAMTOTAL(AMTotal);
        adto.setACODE(amdto.getAMCODE());
        adao.amUpdate(adto);

        return AMTotal;
    }

    public ModelAndView aSearch(int filter, String akey, String aCate) {
        List<AUCTION> aSearchList = null;
        if (filter == 1) {
            aSearchList = adao.aSearchList(akey, aCate); // 코드 최신
        } else if (filter == 2) {
            aSearchList = adao.aSearchList2(akey, aCate); // 코드 과거
        } else if (filter == 3) {
            aSearchList = adao.aSearchList3(akey, aCate); // 가격 높은순
        } else if (filter == 4) {
            aSearchList = adao.aSearchList4(akey, aCate); // 가격 낮은순
        } else if (filter == 5) {
            aSearchList = adao.aSearchList5(akey, aCate); // 인기 높은순
        } else if (filter == 6) {
            aSearchList = adao.aSearchList6(akey, aCate); // 인기 낮은순
        } else if (filter == 7) {
            aSearchList = adao.aSearchList7(akey, aCate); // 시작까지 남은시간
        } else if (filter == 8) {
            aSearchList = adao.aSearchList8(akey, aCate); // 마감까지 남은시간
        } else if (filter == 9) {
            aSearchList = adao.aSearchList9(akey, aCate); // 결제 대기 품목
        }

        mav.addObject("aList", aSearchList);
        if (aCate.equals("photo")) {
            mav.setViewName("A_PhotoList");
        } else if (aCate.equals("illust")) {
            mav.setViewName("A_illustList");
        }
        return mav;
    }

    public int aStUpdate() {
        List<AUCTION> aSt0List = adao.aStSearch0();
        int size0 = aSt0List.size();
        for (int i = 0; i < size0; i++) {
            adao.aStUpdate0(aSt0List.get(i).getACODE());
        }

        List<AUCTION> aSt1List = adao.aStSearch1();
        int size1 = aSt1List.size();


        for (int i = 0; i < size1; i++) {
            adao.aStUpdate1(aSt1List.get(i).getACODE());

            String aSBidEmail = adao.aSearchId(aSt1List.get(i).getACODE());
            String str = "abc";
            String Title = "NFT Store";
            if (aSBidEmail == null) {
                Title = "NFT Store - 경매 실패";
                str = "<h2>안녕하세요, NFT Store 입니다.</h2>" +
                        "<p>상품의 경매가 실패하였습니다.</p>" +
                        "<p>" + aSt1List.get(i).getANAME() + " 상품이 경매에 실패하였습니다.</p>" +
                        "<p>사이트를 방문하셔서 확인 바랍니다.</p>" +
                        "<a>http://localhost:9091/</a>";
            } else {
                Title = "NFT Store - 낙찰 성공";
                str = "<h2>안녕하세요, NFT Store 입니다.</h2>" +
                        "<p>입찰하신 상품의 경매가 완료되었습니다.</p>" +
                        "<p>" + aSt1List.get(i).getANAME() + " 상품를 낙찰받으셨습니다.</p>" +
                        "<p>사이트를 방문하셔서 결제 바랍니다.</p>" +
                        "<a href='http://localhost:9091/'>http://localhost:9091/</a>";
            }
            System.out.println(aSBidEmail + " \n" + Title + "\n " + str);
            MimeMessage mail = mailSender.createMimeMessage();
            try {
                mail.setSubject(Title);
                mail.setText(str, "UTF-8", "html");
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress(aSBidEmail));

                mailSender.send(mail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return size0 + size1;
    }

    public ModelAndView aPayForm(int acode) {
        AUCTION aPay = adao.aView(acode);
        APRICE aPay1 = adao.aPayForm(acode);

        mav.addObject("aPay", aPay);
        mav.addObject("aPay1", aPay1);
        mav.setViewName("A_Pay");
        return mav;
    }

    public int aPay(APRICE apdto) {
        int wallet = adao.aWCount(apdto);
        APRICE Pay = adao.aPayForm(apdto.getAPCODE());
        int PayPrice = Pay.getAPPRICE();

        if (PayPrice > wallet) {
            wallet = wallet - PayPrice;

        } else {
            adao.aWPay(Pay);
            wallet = adao.aWCount(Pay);
            adao.aStUpdate2(Pay.getAPCODE());
        }
        return wallet;
    }
}
