package com.kuaiyou.lucky.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.entity.Prize;
import com.kuaiyou.lucky.mapper.PrizeMapper;
import com.kuaiyou.lucky.service.PrizeService;
import com.kuaiyou.lucky.utils.DateUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Service
public class PrizeServiceImpl extends ServiceImpl<PrizeMapper, Prize> implements PrizeService {

	@Override
	public List<Prize> getRatos(String userid) {
		EntityWrapper<Prize> wrapper = new EntityWrapper<>();
		wrapper.eq(Prize.ATYPE, 2).ne(Prize.RATO, 0).eq(Prize.PUB, 1).in(Prize.TYPE, new Integer[] { 3, 4 });
		List<Prize> list = selectList(wrapper);
		Date date = new Date();
		Date daysago = DateUtil.add(-3);
		List<Prize> nojoin = baseMapper.getRatos(userid, date, daysago);
		HashSet<Prize> hashSet = new HashSet<>();
		hashSet.addAll(list);
		hashSet.addAll(nojoin);
		return new ArrayList<Prize>(hashSet);
	}

	@Override
	public boolean updateALL() {
		EntityWrapper<Prize> wrapper = new EntityWrapper<>();
		wrapper.eq(Prize.ATYPE, 2);
		List<Prize> selectList = selectList(wrapper);
		selectList.forEach(e -> e.setPub(0));
		return updateBatchById(selectList);
	}

	@Override
	public Prize selectByCtype(int i) {
		EntityWrapper<Prize> wrapper = new EntityWrapper<>();
		wrapper.eq(Prize.TYPE, 4).eq(Prize.PUB, 1);
		return selectOne(wrapper);
	}

}
