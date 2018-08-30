package com.simulator.dataservice.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulator.dataservice.entity.TradeInfoEntity;
import com.simulator.dataservice.mapper.TradeInfoMapMapper;
import com.simulator.dataservice.repository.TradeInfoRepository;

@Service
public class TradeInfoService {
    @Autowired
    private TradeInfoMapMapper tradeInfoMapMapper;

    @Autowired
    private TradeInfoRepository tradeInfoRepository;

    /** 取引一覧取得(ログインIdと日付指定). */
    @Transactional
    public List<TradeInfoEntity> selectTradeListByValue(TradeInfoEntity tradeInfoEntity) {

        return tradeInfoMapMapper.selectTradeListByValue(tradeInfoEntity);

    }

    /** 取引詳細情報取得. */
    @Transactional
    public TradeInfoEntity selectByPk(String transactionId){
        return tradeInfoMapMapper.selectByPk(transactionId);
    }


    /** 取引情報登録. */
    @Transactional
    public void insertByValue(TradeInfoEntity tradeInfoEntity) {

       tradeInfoMapMapper.insertByValue(tradeInfoEntity);

    }

    /** 取引ステータス更新. */
    @Transactional
    public void updateStsByPk(TradeInfoEntity tradeInfoEntity) {

       tradeInfoMapMapper.updateStsByPk(tradeInfoEntity);

    }

    /** 取引詳細情報取得. */
    @Transactional
    public TradeInfoEntity getTradeInfo(String transactionId){
        return tradeInfoRepository.getOne(transactionId);
    }

    /** 変更情報保存取得. */
    @Transactional
    public void save(TradeInfoEntity tradeInfoEntity){
        tradeInfoRepository.save(tradeInfoEntity);
    }

    /** 取引情報を取得する. */
    @Transactional
    public List<TradeInfoEntity> findAll() {

        return tradeInfoRepository.findAll();

    }

    /** 取引情報を取得する. */
    @Transactional
    public void deleteById(String transactionId) {

        tradeInfoRepository.deleteById(transactionId);

    }



}