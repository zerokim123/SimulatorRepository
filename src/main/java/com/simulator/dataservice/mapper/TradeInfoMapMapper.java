package com.simulator.dataservice.mapper;

import java.util.List;

import com.simulator.dataservice.entity.TradeInfoEntity;

public interface TradeInfoMapMapper {

    List<TradeInfoEntity> selectTradeListByValue(TradeInfoEntity transactionId);

    TradeInfoEntity selectByPk(String transactionId);

    void insertByValue(TradeInfoEntity tradeInfoEntity);

    void updateStsByPk(TradeInfoEntity tradeInfoEntity);

}
