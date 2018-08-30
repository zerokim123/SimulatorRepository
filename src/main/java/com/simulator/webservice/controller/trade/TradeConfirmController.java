package com.simulator.webservice.controller.trade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simulator.dataservice.Service.TradeInfoService;
import com.simulator.dataservice.entity.TradeInfoEntity;

@Controller
@RequestMapping("/trades")
public class TradeConfirmController {

    @Autowired
    private TradeInfoService tradeInfoService;

    /**
     * ステータス選択の表示に使用するアイテム
     */
    final static Map<String, String> SELECT_STATUS = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("0(未取消)", "0");
                    put("1(取消済み)", "1");
                }
            });

    /**
     * ステータスの表示に使用するアイテム
     */
    final static Map<String, String> DISPLAY_STATUS = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("0", "0(未取消)");
                    put("1", "1(取消済み)");
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
                    put("2:取消", "2");
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
                    put("2", "2:取消");
                    put("3", "3:失敗");
                    put("4", "4:未達");
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

    @GetMapping
    public String index(Model model) {

        List<TradeInfoEntity> info = tradeInfoService.findAll();

        List<TradeInfoEntity> scereenList = new ArrayList<TradeInfoEntity>();

        for (TradeInfoEntity tradeInfoDto : info) {
            tradeInfoDto.setIncentiveOfferType(DISPLAY_Offer_Type.get(tradeInfoDto.getIncentiveOfferType()));
            tradeInfoDto.setRegistCancelDivision(DISPLAY_REGIST_CANCEL_DIVISION.get(tradeInfoDto.getRegistCancelDivision()));
            tradeInfoDto.setStatus(DISPLAY_STATUS.get(tradeInfoDto.getStatus()));
            scereenList.add(tradeInfoDto);
        }

        model.addAttribute("tradeInfo", scereenList);

        return "trades/tradeInfo";
    }


    @GetMapping("{transactionId}/edit")
    public String edit(@PathVariable String transactionId, Model model) { // ⑤
        TradeInfoEntity tradeInfo = tradeInfoService.getTradeInfo(transactionId);
        model.addAttribute("tradeInfo", tradeInfo);
        model.addAttribute("selectStatus", SELECT_STATUS);
        model.addAttribute("selectRCD", SELECT_REGIST_CANCEL_DIVISION);
        return "trades/edit";
    }

    @GetMapping("{transactionId}")
    public String show(@PathVariable String transactionId, Model model) {
        TradeInfoEntity tradeInfo = tradeInfoService.getTradeInfo(transactionId);
        tradeInfo.setIncentiveOfferType(DISPLAY_Offer_Type.get(tradeInfo.getIncentiveOfferType()));
        tradeInfo.setRegistCancelDivision(DISPLAY_REGIST_CANCEL_DIVISION.get(tradeInfo.getRegistCancelDivision()));
        tradeInfo.setStatus(DISPLAY_STATUS.get(tradeInfo.getStatus()));
        model.addAttribute("tradeInfo", tradeInfo);
        return "trades/show";
    }


    @PutMapping("{transactionId}")
    public String update(@PathVariable String transactionId, @ModelAttribute TradeInfoEntity tradeInfoEntity) {

        TradeInfoEntity info = tradeInfoService.getTradeInfo(transactionId);
        info.setRegistCancelDivision(tradeInfoEntity.getRegistCancelDivision());
        info.setStatus(tradeInfoEntity.getStatus());
        tradeInfoService.save(info);
        return "redirect:/trades";
    }

    @DeleteMapping("{transactionId}")
    public String destroy(@PathVariable String transactionId) {
        tradeInfoService.deleteById(transactionId);
        return "redirect:/trades";
    }

}
