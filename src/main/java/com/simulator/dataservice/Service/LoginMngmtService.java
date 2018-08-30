package com.simulator.dataservice.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulator.dataservice.entity.LoginMngmtEntity;
import com.simulator.dataservice.mapper.LoginMngmtMapper;
import com.simulator.dataservice.repository.LoginMngmtRepository;

@Service
public class LoginMngmtService {
    @Autowired
    private LoginMngmtMapper loginMngmtMapper;

    @Autowired
    private LoginMngmtRepository loginMngmtRepository;

    /** ログインパスワード更新. */
    @Transactional
    public void updateLoginPassByPK(LoginMngmtEntity loginMngmtBean) {

        loginMngmtMapper.updateLoginPassByPK(loginMngmtBean);

    }

    public List<LoginMngmtEntity> findAll() {
        return loginMngmtRepository.findAll();

    }

    public LoginMngmtEntity selectByPk(String terminalLoginId) {
        return loginMngmtRepository.findByTerminalLoginId(terminalLoginId);

    }

    @Transactional
    public void save(LoginMngmtEntity loginMngmtEntity) {

        loginMngmtRepository.save(loginMngmtEntity);

    }

    public LoginMngmtEntity findByPk(String terminalLoginId) {
        return loginMngmtRepository.findByTerminalLoginId(terminalLoginId);

    }

    public LoginMngmtEntity getOne(String terminalLoginId) {
        return loginMngmtRepository.getOne(terminalLoginId);

    }

    @Transactional
    public void delete(String terminalLoginId) {
       loginMngmtRepository.deleteById(terminalLoginId);
    }

    @Transactional
    public void insertValu(LoginMngmtEntity loginMngmtMapEntity) {
        loginMngmtMapper.insertValu(loginMngmtMapEntity);
    }


}