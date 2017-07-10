package com.ginkgocap.ywxt.video.interceptor;

import com.alibaba.fastjson.JSON;
import com.ginkgocap.ywxt.video.utils.IPUtils;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问日志拦截器
 * @author aihua
 */
@Component
public class AccessLogInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Logger accesslogLogger = LoggerFactory.getLogger("accessLog");

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
                AccessLog accessLog = new AccessLog();

                accessLog.setToken(null);
                accessLog.setSessionId(WebUtils.getSessionId(request));
                accessLog.setIp(IPUtils.getRemoteAddr(request));
                accessLog.setUrl(request.getRequestURL().toString() + (request.getQueryString() == null ? "" : ("?" + request.getQueryString())));
                accessLog.setUserId(null);
                accessLog.setReferer(request.getHeader("Referer"));
                accessLog.setIsAjax("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) ? 1 : 0);
                accessLog.setMethod(request.getMethod());
                accessLog.setUserAgent(request.getHeader("User-Agent"));
                accessLog.setParamFrom(request.getParameter("from"));

                if (accesslogLogger.isInfoEnabled()) {
                    accesslogLogger.info(accessLog.toString());
                }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return true;
    }

    class AccessLog{
        private Long id;

        private String module = "video";
        private String token;
        private String sessionId;
        private String ip;
        private String url;
        private Long userId;
        private String referer;
        private Integer isAjax;
        private String method;
        private String userAgent;
        private String paramFrom;

        public AccessLog() {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getReferer() {
            return referer;
        }

        public void setReferer(String referer) {
            this.referer = referer;
        }

        public Integer getIsAjax() {
            return isAjax;
        }

        public void setIsAjax(Integer isAjax) {
            this.isAjax = isAjax;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        public String getParamFrom() {
            return paramFrom;
        }

        public void setParamFrom(String paramFrom) {
            this.paramFrom = paramFrom;
        }
        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}
