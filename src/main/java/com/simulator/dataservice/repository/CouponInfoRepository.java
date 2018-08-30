package com.simulator.dataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.simulator.dataservice.entity.CouponInfoEntity;

@Repository
public interface CouponInfoRepository
        extends JpaRepository<CouponInfoEntity, String>,  JpaSpecificationExecutor<CouponInfoEntity>, CrudRepository<CouponInfoEntity, String> {

}
