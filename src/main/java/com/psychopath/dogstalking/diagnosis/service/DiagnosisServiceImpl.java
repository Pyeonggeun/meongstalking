package com.psychopath.dogstalking.diagnosis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.diagnosis.mapper.DiagnosisSqlMapper;

@Service
public class DiagnosisServiceImpl {
    @Autowired
	private DiagnosisSqlMapper diagnosisSqlMapper;
}
