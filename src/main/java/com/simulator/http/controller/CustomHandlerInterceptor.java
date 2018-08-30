package com.simulator.http.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.GsonBuilder;
import com.simulator.common.CommonConst;
import com.simulator.common.NullStringToEmptyAdapterFactory;
import com.simulator.common.RequestJsonCheckUtil;
import com.simulator.common.bean.ActionResult;

public class CustomHandlerInterceptor extends HandlerInterceptorAdapter {

    /** ロガー */
    private static final Logger log = LoggerFactory.getLogger(CustomHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("controller前処理開始");
        boolean preHandleFlg = true;
        String postData = MDC.get("postData");
        String postURI = request.getRequestURI();
        log.info("ClientInfo:["+ request.getRemoteAddr() +":"+ request.getRemotePort() + "]" );
        log.info("PostURI:" + postURI);
        log.info("PsotData:" + postData);

        if (CommonConst.PATH_PATTERN.contains(postURI)) {
            if (postData == null) {
                setResp(response, 1, "E99999", "クーポンサービス利用できません。");
                log.info("controller前処理終了");
                return false;
            }

            boolean jsonCheck = RequestJsonCheckUtil.requestCheck(postData);

            if (!jsonCheck) {
                setResp(response, 1, "E88888", "クーポンサービス利用できません。");
                preHandleFlg = false;
            }
        }

        log.info("controller前処理終了");
        return preHandleFlg;
    }

    /**
     * 後処理
     *
     * @param request リクエスト
     * @param response レスポンス
     * @param handler handler
     * @param modelAndView ModelAndView
     * @throws Exception Exception
     */
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        MDC.clear();
    }

    /**
     * DispatcherServlet完全に実行したら、下記の関数を実行する
     *
     * @param request リクエスト
     * @param response レスポンス
     * @param handler handler
     * @param ex 異常
     * @throws Exception Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    /**
     * ログインしない場合、レスポンースを設定する
     *
     * @param response レスポンス
     */
    private void setResp(HttpServletResponse response, int resultCode, String errorCode, String errorMesage) {
        ActionResult result = new ActionResult();
        result.setResultCode(resultCode);
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMesage);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(new GsonBuilder().registerTypeAdapterFactory(
                    new NullStringToEmptyAdapterFactory<Object>()).serializeNulls().create().toJson(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }

            // ローカルメモリをクリアする
            MDC.clear();
        }
    }
}