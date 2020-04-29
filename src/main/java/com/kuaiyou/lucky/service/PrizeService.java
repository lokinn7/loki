package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.entity.Prize;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
public interface PrizeService extends IService<Prize> {

	List<Prize> getRatos(String userid);

	boolean updateALL();

	Prize selectByCtype(int i);

}
