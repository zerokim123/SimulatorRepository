package com.simulator.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.simulator.dataservice.Service.TradeSeqService;
import com.simulator.dataservice.entity.CouponInfoEntity;
import com.simulator.dataservice.repository.CouponInfoRepository;


@RestController
public class TestController {

   @Autowired
   private CouponInfoRepository couponInfo;

   @Autowired
   private TradeSeqService tradeSeqService;



  @RequestMapping("/test")
  @ResponseBody
  public String home() {
      String returnStr = "";
      Iterable<CouponInfoEntity> list = couponInfo.findAll();
      for (CouponInfoEntity testBean : list) {
          returnStr += "id  :" + testBean.getPartnerCompUserId();
          returnStr += "findOne" + couponInfo.findById(testBean.getPartnerCompUserId());
      }

      CouponInfoEntity insertNew = new CouponInfoEntity();

      insertNew.setPartnerCompUserId("TestID123456");

      couponInfo.save(insertNew);

      returnStr += tradeSeqService.getSeq();
      return returnStr;
  }
}