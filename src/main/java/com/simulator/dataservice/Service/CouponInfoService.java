package com.simulator.dataservice.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulator.dataservice.entity.CouponInfoEntity;
import com.simulator.dataservice.mapper.CouponInfoMapper;
import com.simulator.dataservice.repository.CouponInfoRepository;

@Service
public class CouponInfoService {
    @Autowired
    private CouponInfoRepository couponInfoRepository;

    @Autowired
    private CouponInfoMapper couponInfoMapper;

    /** クーポン一覧取得. */
    @Transactional
    public List<CouponInfoEntity> findAll() {
        return couponInfoRepository.findAll();

    }

    /** クーポン情報取得. */
    @Transactional
    public CouponInfoEntity findOne(String partnerCompUserId) {
        return couponInfoRepository.getOne(partnerCompUserId);
    }

    /** クーポン情報保存. */
    @Transactional
    public CouponInfoEntity save(CouponInfoEntity couponInfoEntity) {
        return couponInfoRepository.save(couponInfoEntity);
    }

    /** クーポン情報保存. */
    @Transactional
    public void saveAll(List<CouponInfoEntity> couponInfoEntityList) {
        couponInfoRepository.saveAll(couponInfoEntityList);
    }

    /** クーポン削除. */
    @Transactional
    public void delete(String partnerCompUserId) {
        couponInfoRepository.deleteById(partnerCompUserId);
    }

    /** クーポン情報登録. */
    @Transactional
    public void insertByValu(CouponInfoEntity couponInfoMapEntity) {
        couponInfoMapper.insertByValu(couponInfoMapEntity);
    }

}