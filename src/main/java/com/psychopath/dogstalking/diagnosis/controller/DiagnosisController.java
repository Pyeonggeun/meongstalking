package com.psychopath.dogstalking.diagnosis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.psychopath.dogstalking.diagnosis.service.DiagnosisServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/diagnosis")
public class DiagnosisController {

    @Autowired
    private DiagnosisServiceImpl diagnosisService;

    @RequestMapping("diagnosisMainPage")
    public String diagnosisMainPage(HttpSession session) {
        return "diagnosis/diagnosisMainPage";
    }

    @RequestMapping("diagnosisSkinPage")
    public String diagnosisSkinPage(HttpSession session) {
        return "diagnosis/diagnosisSkinPage";
    }

   //@RequestMapping(value = "/diagnosisSkinResultPage", method = RequestMethod.GET)
   @RequestMapping("diagnosisSkinResultPage")
    public String diagnosisSkinResultPage(HttpSession session) {
        return "diagnosis/diagnosisSkinResultPage";
    }
    

}
