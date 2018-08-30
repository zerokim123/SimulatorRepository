package com.simulator.webservice.controller.userconfirm;

import java.util.ArrayList;
import java.util.Collections;
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

import com.simulator.common.CommonUtil;
import com.simulator.dataservice.Service.LoginMngmtService;
import com.simulator.dataservice.entity.LoginMngmtEntity;

@Controller
@RequestMapping("/users")
public class UserConfirmController {

    @Autowired
    private LoginMngmtService loginMngmtService;

    private static String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

    private static String TIME_FORMAT_YMD = "yyyy/MM/dd";

    /**
     * ログイン制御の表示に使用するアイテム
     */
    final static Map<String, String> DISPLAY_LOGIN_CONTROL = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("0", "0:正常");
                    put("1", "1:初期");
                    put("2", "2:通信ｴﾗｰ408");
                    put("3", "3:通信ｴﾗｰ504");
                    put("4", "4:通信ｴﾗｰ400");
                    put("5", "5:異常");
                }
            });
    /**
     * ログイン制御選択の表示に使用するアイテム
     */
    final static Map<String, String> SELECT_LOGIN_CONTROL = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("0:正常", "0");
                    put("1:初期", "1");
                    put("2:通信ｴﾗｰ408", "2");
                    put("3:通信ｴﾗｰ504", "3");
                    put("4:通信ｴﾗｰ400", "4");
                    put("5:異常", "5");
                }
            });

    /**
     * 業務制御の表示に使用するアイテム
     */
    final static Map<String, String> DISPLAY_TRADE_CONTROL = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("0", "0:正常");
                    put("1", "1:異常");
                    put("2", "2:通信ｴﾗｰ408");
                    put("3", "3:通信ｴﾗｰ504");
                    put("4", "4:通信ｴﾗｰ400");
                }
            });

    /**
     * 業務制御選択の表示に使用するアイテム
     */
    final static Map<String, String> SELECT_TRADE_CONTROL = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("0:正常", "0");
                    put("1:異常", "1");
                    put("2:通信ｴﾗｰ408", "2");
                    put("3:通信ｴﾗｰ504", "3");
                    put("4:通信ｴﾗｰ400", "4");
                }
            });

    @GetMapping
    public String index(Model model) {

        List<LoginMngmtEntity> info = loginMngmtService.findAll();

        ArrayList<LoginMngmtDto> scereenList = new ArrayList<LoginMngmtDto>();

        for (LoginMngmtEntity wkInfo : info) {
            LoginMngmtDto loginInfo = new LoginMngmtDto();
            BeanUtils.copyProperties(wkInfo, loginInfo);
            String date = CommonUtil.formattedTimestamp(wkInfo.getExpDate(), TIME_FORMAT);
            loginInfo.setDisplayExpDate(date);
            scereenList.add(loginInfo);
        }

        model.addAttribute("userInfo", scereenList);

        return "users/userInfo";
    }

    @GetMapping("new")
    public String newCouponr(Model model) {
        model.addAttribute("selectLoginItems", SELECT_LOGIN_CONTROL);
        model.addAttribute("selectTradeContItems", SELECT_TRADE_CONTROL);
        return "users/new";
    }

    @GetMapping("{terminalLoginId}")
    public String show(@PathVariable String terminalLoginId, Model model) {
        LoginMngmtEntity userInfo = loginMngmtService.selectByPk(terminalLoginId);
        LoginMngmtDto loginUserInfo = new LoginMngmtDto();
        BeanUtils.copyProperties(userInfo, loginUserInfo);
        String date = CommonUtil.formattedTimestamp(userInfo.getExpDate(), TIME_FORMAT);
        loginUserInfo.setDisplayExpDate(date);
        loginUserInfo.setLoginControl(DISPLAY_LOGIN_CONTROL.get(userInfo.getLoginControl()));
        loginUserInfo.setUpdateControl(DISPLAY_TRADE_CONTROL.get(userInfo.getUpdateControl()));
        loginUserInfo.setConfirmControl(DISPLAY_TRADE_CONTROL.get(userInfo.getConfirmControl()));
        loginUserInfo.setUseOffControl(DISPLAY_TRADE_CONTROL.get(userInfo.getUseOffControl()));
        loginUserInfo.setUseOnControl(DISPLAY_TRADE_CONTROL.get(userInfo.getUseOnControl()));
        loginUserInfo.setRusultControl(DISPLAY_TRADE_CONTROL.get(userInfo.getRusultControl()));
        loginUserInfo.setCancelControl(DISPLAY_TRADE_CONTROL.get(userInfo.getCancelControl()));
        loginUserInfo.setDetailControl(DISPLAY_TRADE_CONTROL.get(userInfo.getDetailControl()));
        model.addAttribute("userInfo", loginUserInfo);
        return "users/show";
    }

    @GetMapping("{terminalLoginId}/edit")
    public String edit(@PathVariable String terminalLoginId, Model model) { // ⑤
        LoginMngmtEntity userInfo = loginMngmtService.selectByPk(terminalLoginId);
        LoginMngmtDto loginUserInfo = new LoginMngmtDto();
        BeanUtils.copyProperties(userInfo, loginUserInfo);
        String date = CommonUtil.formattedTimestamp(userInfo.getExpDate(), TIME_FORMAT_YMD);
        loginUserInfo.setDisplayExpDate(date);
        model.addAttribute("userInfo", loginUserInfo);
        model.addAttribute("selectLoginItems", SELECT_LOGIN_CONTROL);
        model.addAttribute("selectTradeContItems", SELECT_TRADE_CONTROL);
        return "users/edit";
    }

    @PostMapping
    public String create(@ModelAttribute LoginMngmtDto loginUserInfo) { // ⑥
        LoginMngmtEntity insertInfo = new LoginMngmtEntity();
        BeanUtils.copyProperties(loginUserInfo, insertInfo);
        insertInfo.setExpDate(CommonUtil.formattedString(loginUserInfo.getDisplayExpDate(), TIME_FORMAT_YMD));
        loginMngmtService.insertValu(insertInfo);
        return "redirect:/users"; // ⑦
    }

    @PutMapping("{terminalLoginId}")
    public String update(@PathVariable String terminalLoginId, @ModelAttribute LoginMngmtDto loginUserInfo) {
        LoginMngmtEntity userInfoUpdate = new LoginMngmtEntity();
        BeanUtils.copyProperties(loginUserInfo, userInfoUpdate);
        userInfoUpdate.setExpDate(CommonUtil.formattedString(loginUserInfo.getDisplayExpDate(), TIME_FORMAT_YMD));
        loginMngmtService.save(userInfoUpdate);
        return "redirect:/users";
    }

    @DeleteMapping("{terminalLoginId}")
    public String destroy(@PathVariable String terminalLoginId) {
        loginMngmtService.delete(terminalLoginId);
        return "redirect:/users";
    }


}
