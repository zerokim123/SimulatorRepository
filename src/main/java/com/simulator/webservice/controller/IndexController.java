package com.simulator.webservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simulator.dataservice.Service.CouponInfoService;
import com.simulator.dataservice.entity.CouponInfoEntity;
import com.simulator.webservice.controller.from.ResetInfoFrom;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private CouponInfoService couponInfoService;

    @GetMapping
    public String index(Model model) {
        List<CouponInfoEntity> info = couponInfoService.findAll();

        ArrayList<String> couponList = new ArrayList<String>();

        for (CouponInfoEntity couponInfoEntity : info) {

            couponList.add(couponInfoEntity.getPartnerCompUserId());
        }

        CouponInfoEntity infoEntity = new CouponInfoEntity();
        infoEntity.setPartnerCompUserId(null);

        model.addAttribute("resetInfo", infoEntity);

        model.addAttribute("displayList", couponList);

        return "index";
    }

    @PutMapping("{partnerCompUserId}")
    public String update(@PathVariable String partnerCompUserId, @ModelAttribute ResetInfoFrom resetInfoFrom) {

        CouponInfoEntity updateInfo = new CouponInfoEntity();
        String userId = resetInfoFrom.getPartnerCompUserId().substring(1);

        updateInfo = couponInfoService.findOne(userId);

        if (resetInfoFrom.getBalance() != null) {

            updateInfo.setBalance(updateInfo.getBalance() + resetInfoFrom.getBalance());

        }

        if (resetInfoFrom.getCouponRemainUseNum() != null) {
            updateInfo.setCouponRemainUseNum(updateInfo.getCouponRemainUseNum() + resetInfoFrom.getCouponRemainUseNum());
        }
        couponInfoService.save(updateInfo);

        return "redirect:/";
    }

    @PutMapping("reset")
    public String reset(@ModelAttribute ResetInfoFrom resetInfoFrom) {

        List<CouponInfoEntity> wkResetInfo = couponInfoService.findAll();

        List<CouponInfoEntity> resetInfo = new  ArrayList<CouponInfoEntity>();

        for (CouponInfoEntity couponInfoEntity : wkResetInfo) {

            if (resetInfoFrom.getBalance() != null) {

                couponInfoEntity.setBalance(resetInfoFrom.getBalance());
            }

            if (resetInfoFrom.getCouponRemainUseNum() != null) {
                couponInfoEntity.setCouponRemainUseNum(resetInfoFrom.getCouponRemainUseNum());
            }
            resetInfo.add(couponInfoEntity);

        }

        couponInfoService.saveAll(resetInfo);

        return "redirect:/";
    }


}
