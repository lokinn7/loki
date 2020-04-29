package com.kuaiyou.lucky.service.impl;

import com.kuaiyou.lucky.entity.Coins;
import com.kuaiyou.lucky.entity.Convert;
import com.kuaiyou.lucky.entity.Goods;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.mapper.ConvertMapper;
import com.kuaiyou.lucky.service.CoinsService;
import com.kuaiyou.lucky.service.ConvertService;
import com.kuaiyou.lucky.service.GoodsService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-10-17
 */
@Service
public class ConvertServiceImpl extends ServiceImpl<ConvertMapper, Convert> implements ConvertService {

	@Autowired
	CoinsService coinsService;

	@Autowired
	GoodsService goodsService;

	@Override
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public boolean saveItemWithUserCoins(Convert convert, Goods goods) {
		convert.setCtime(new Date());
		boolean flag = insert(convert);
		if (flag && goods != null) {
			Coins coins = new Coins();
			coins.setAmount(goods.getAmount());
			coins.setUserid(convert.getUserid());
			coins.setCtype(CoinTypeEnum.GOODSFEE.getCode());
			boolean updateTemp = coinsService.updateTemp(coins);
			if (!updateTemp) {
				throw new IllegalArgumentException("update user coins happend exception.Please Check!");
			}
		}
		return flag;
	}

	@Override
	public List<Convert> selectByUserid(String userid) {
		EntityWrapper<Convert> wrapper = new EntityWrapper<>();
		wrapper.eq(Convert.USERID, userid);
		return selectList(wrapper);
	}

	@Override
	public List<Convert> selectByUseridAndSkuid(Long skuid, String userid) {
		EntityWrapper<Convert> wrapper = new EntityWrapper<>();
		wrapper.eq(Convert.USERID, userid).eq(Convert.SKUID, skuid);
		return selectList(wrapper);
	}

}
