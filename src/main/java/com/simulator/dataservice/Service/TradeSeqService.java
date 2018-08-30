package com.simulator.dataservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulator.dataservice.mapper.TradeSeqMapper;

@Service
public class TradeSeqService {

    @Autowired
    private TradeSeqMapper tradeSeqMapper;

    /** 取引シーケンス取得. */
    @Transactional
    public String getSeq(){
        return String.valueOf(tradeSeqMapper.getSeq());

    }

}
