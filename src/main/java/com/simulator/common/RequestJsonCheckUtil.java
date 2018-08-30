package com.simulator.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;

import com.google.gson.Gson;
import com.simulator.common.util.SHAUtil;



public class RequestJsonCheckUtil {

    private static final Logger log = Logger.getLogger(RequestJsonCheckUtil.class);

    @SuppressWarnings("unchecked")
    public static boolean requestCheck(String postData) {
        if (postData == null) {
            log.error("PostDataがありません。");
            return false;
        }

        Gson gson = new Gson();

        Map<String, String> params = gson.fromJson(
                        postData, Map.class);

        List<Map.Entry<String, String>> filedlist =
                new ArrayList<Map.Entry<String, String>>(
                        params.entrySet());

        // ASKIIでソートする
        Collections.sort(
                filedlist, new Comparator<Map.Entry<String, String>>() {
            /* (non-Javadoc)
             * @see java.util.Comparator#compare(
             * java.lang.Object, java.lang.Object)
             */
            /**
             * compare.
             *
             * @param o1 o1
             * @param o2 o2
             * @return int
             */
            public int compare(Map.Entry<String, String> o1,
                    Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        StringBuilder requestParamSb = new StringBuilder();
        // 取得したリストからSIGN文字を確認する
        for (int i = 0; i < filedlist.size(); i++){
            if ("identityInfoText".equals(filedlist.get(i).getKey()))
            {
                continue;
            }

            requestParamSb.append(filedlist.get(i).getKey());
            requestParamSb.append("=");
            requestParamSb.append(filedlist.get(i).getValue());

            if (i != filedlist.size() - 1) {
                requestParamSb.append("&");
            }
        }

     // 識別情報を判断
        boolean shaFlag = SHAUtil.CheckSign(
                requestParamSb.toString(),
                params.get("identityInfoText"));

        // 一致しない場合、ログインしないと識別、処理を中止する
        if (!shaFlag)
        {
            return false;
        }

        return true;
    }

}
