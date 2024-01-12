package com.psychopath.dogstalking.mstar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.psychopath.dogstalking.mstar.mapper.MstarSqlMapper;

@Service
public class MstarServiceImpl {
    @Autowired
    private MstarSqlMapper mstarSqlMapper;
    
    
}
