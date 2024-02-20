package com.psychopath.dogstalking.commons.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class HandleErorPageController {

    @RequestMapping("/erorPage/404")
    public String error404(HttpServletRequest req, HttpServletResponse resp) {
        log.info("errorPage 404");
        printErrorInfo(req);
        return "commons/interceptor/eror404Page";
    }

    @RequestMapping("/erorPage/500")
    public String error500(HttpServletRequest req, HttpServletResponse resp) {
        log.info("errorPage 500");
        printErrorInfo(req);
        return "commons/interceptor/eror500Page";
    }

    private void printErrorInfo(HttpServletRequest req) {
        log.info("dispatchTypes= {}", req.getDispatcherType());
    }
    @RequestMapping("/commons/interceptor/sessionNullPage")
    public String nullPage() {
        
        return "commons/interceptor/sessionNullPage";
    }

}


