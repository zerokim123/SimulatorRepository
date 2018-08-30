package com.simulator.dataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.simulator.dataservice.entity.LoginMngmtEntity;


public interface LoginMngmtRepository
        extends JpaRepository<LoginMngmtEntity, String> , CrudRepository<LoginMngmtEntity, String>, JpaSpecificationExecutor<LoginMngmtEntity> {

    LoginMngmtEntity findByTerminalLoginId(String terminalLoginId);

}
