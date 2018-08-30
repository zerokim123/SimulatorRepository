package com.simulator.dataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.simulator.dataservice.entity.TradeInfoEntity;

@Repository
public interface TradeInfoRepository
        extends JpaRepository<TradeInfoEntity, String>, JpaSpecificationExecutor<TradeInfoEntity>, CrudRepository<TradeInfoEntity, String>{

}
