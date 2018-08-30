package com.simulator.dataservice.mapper;

import com.simulator.dataservice.entity.LoginMngmtEntity;

public interface LoginMngmtMapper {

    void updateLoginPassByPK(LoginMngmtEntity loginMngmtEntity);

    void insertValu(LoginMngmtEntity loginMngmtEntity);

}
