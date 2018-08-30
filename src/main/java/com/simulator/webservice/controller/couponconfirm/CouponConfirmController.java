package com.simulator.webservice.controller.couponconfirm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simulator.dataservice.Service.CouponInfoService;
import com.simulator.dataservice.entity.CouponInfoEntity;

@Controller
@RequestMapping("/coupons")
public class CouponConfirmController {

    @Autowired
    private CouponInfoService couponInfoService;

    /**
     * インセンティブ提供方法選択の表示に使用するアイテム
     */
    final static Map<String, String> SELECT_Offer_Type = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("0(割引)", "0");
                    put("1(キャッシュバック)", "1");
                    put("2(ギフト)", "2");
                }
            });

    /**
     * インセンティブ提供方の表示に使用するアイテム
     */
    final static Map<String, String> DISPLAY_Offer_Type = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("0", "0(割引)");
                    put("1", "1(キャッシュバック)");
                    put("2", "2(ギフト)");
                }
            });

    /**
     * 金額入力制御選択の表示に使用するアイテム
     */
    final static Map<String, String> SELECT_kingkExistFlg = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("0(なし)", "0");
                    put("1(あり)", "1");
                }
            });

    /**
     * 金額入力制御表示に使用するアイテム
     */
    final static Map<String, String> DISPLAY_kingkExistFlg = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("0", "0(なし)");
                    put("1", "1(あり)");
                }
            });


    /**
     * 取引区分選択の表示に使用するアイテム
     */
    final static Map<String, String> SELECT_REGIST_CANCEL_DIVISION = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("1:利用", "1");
                    put("3:失敗", "3");
                    put("4:未達", "4");
                }
            });
    /**
     * 取引区分の表示に使用するアイテム
     */
    final static Map<String, String> DISPLAY_REGIST_CANCEL_DIVISION = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("1", "1:利用");
                    put("3", "3:失敗");
                    put("4", "4:未達");
                }
            });

    private final Map<String, String> kingkExistFlgStr = new HashMap<String, String>() {
        /**
         *
         */
        private static final long serialVersionUID = 8161703100044204711L;
        {
            put("0", "0(なし)");
        }
        {
            put("1", "1(あり)");
        }
    };

    @GetMapping
    public String index(Model model) {

        List<CouponInfoEntity> info = couponInfoService.findAll();

        List<CouponInfoDto> scereenList = new ArrayList<CouponInfoDto>();

        for (CouponInfoEntity couponInfoDto : info) {
            CouponInfoDto wkScereenInfo = new CouponInfoDto();
            BeanUtils.copyProperties(couponInfoDto, wkScereenInfo);
            wkScereenInfo.setKingkExistFlg(kingkExistFlgStr.get(wkScereenInfo.getKingkExistFlg()));
            wkScereenInfo.setIncentiveOfferType(DISPLAY_Offer_Type.get(wkScereenInfo.getIncentiveOfferType()));
            scereenList.add(wkScereenInfo);
        }

        model.addAttribute("couponInfo", scereenList);

        return "coupons/couponsInfo";
    }

    @GetMapping("new")
    public String newCouponr(Model model) {
        model.addAttribute("selectItems", SELECT_Offer_Type);
        model.addAttribute("selectKingkItems", SELECT_kingkExistFlg);
        model.addAttribute("selectTradeItems", SELECT_REGIST_CANCEL_DIVISION);
        return "coupons/new";
    }

    @GetMapping("{partnerCompUserId}/edit")
    public String edit(@PathVariable String partnerCompUserId, Model model) { // ⑤
        CouponInfoEntity couponInfo = couponInfoService.findOne(partnerCompUserId);
        model.addAttribute("couponInfo", couponInfo);
        model.addAttribute("selectItems", SELECT_Offer_Type);
        model.addAttribute("selectKingkItems", SELECT_kingkExistFlg);
        model.addAttribute("selectTradeItems", SELECT_REGIST_CANCEL_DIVISION);
        return "coupons/edit";
    }

    @GetMapping("{partnerCompUserId}")
    public String show(@PathVariable String partnerCompUserId, Model model) {
        CouponInfoEntity couponInfo = couponInfoService.findOne(partnerCompUserId);
        couponInfo.setIncentiveOfferType(DISPLAY_Offer_Type.get(couponInfo.getIncentiveOfferType()));
        couponInfo.setKingkExistFlg(DISPLAY_kingkExistFlg.get(couponInfo.getKingkExistFlg()));
        couponInfo.setRegistCancelDivision(DISPLAY_REGIST_CANCEL_DIVISION.get(couponInfo.getRegistCancelDivision()));
        model.addAttribute("couponInfo", couponInfo);
        return "coupons/show";
    }

    @PostMapping
    public String create(@ModelAttribute CouponInfoEntity couponInfoMapEntity) { // ⑥
        couponInfoService.insertByValu(couponInfoMapEntity);
        return "redirect:/coupons"; // ⑦
    }

    @PutMapping("{partnerCompUserId}")
    public String update(@PathVariable String partnerCompUserId, @ModelAttribute CouponInfoEntity couponInfoEntity) {
        couponInfoEntity.setPartnerCompUserId(partnerCompUserId);
        couponInfoService.save(couponInfoEntity);
        return "redirect:/coupons";
    }

    @DeleteMapping("{partnerCompUserId}")
    public String destroy(@PathVariable String partnerCompUserId) {
        couponInfoService.delete(partnerCompUserId);
        return "redirect:/coupons";
    }

}
