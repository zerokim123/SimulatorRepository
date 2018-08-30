package com.simulator.common;

import java.util.Arrays;
import java.util.List;

public class CommonConst {

    /** クーポン消込URL*/
    public static final String USE = "/cafistravelercenter/app/coupon/use";

    /** クーポン利用確認URL*/
    public static final String CONFIRM = "/cafistravelercenter/app/coupon/confirm";

    /** クーポン利用取消URL*/
    public static final String CANCEL = "/cafistravelercenter/app/coupon/cancel";

    /** パスワード変更URL*/
    public static final String UPDATE = "/cafistravelercenter/app/user/password";

    /** ログイン情報URL*/
    public static final String LOGIN = "/cafistravelercenter/app/user/login";

    /** 取引一覧照会URL*/
    public static final String RESULT = "/cafistravelercenter/app/coupon/result";

    /**取引詳細照会URL*/
    public static final String DETAIL = "/cafistravelercenter/app/coupon/detail";

    /** SHAキー*/
    public static final String SHA_KEY = "hhjjhhjk54pa43blzy1jkiopk8jqdaon";

    public static final List<String> PATH_PATTERN = Arrays.asList(USE, CONFIRM, CANCEL, UPDATE, LOGIN, RESULT, DETAIL);

}
