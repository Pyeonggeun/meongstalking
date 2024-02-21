package com.psychopath.dogstalking.commons.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // preHandle 로직
        System.out.println("postHandle 실행");
        // Object sessionUser = request.getSession().getAttribute("sessionUser");
        // if (sessionUser == null && !response.isCommitted()) {
        //     // 세션 만료 시 다른 페이지로 리다이렉트
        //     response.sendRedirect("/commons/interceptor/sessionNullPage");
        //     return false; // 리다이렉트 이후에는 더 이상의 코드 실행을 막음
        // }
        // System.out.println("실행됨2");
        return true;
    }

}
