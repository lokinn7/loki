package com.kuaiyou.lucky.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kuaiyou.lucky.entity.Adsenselog;
import com.kuaiyou.lucky.entity.Dailyreport;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.res.ActiveRes;
import com.kuaiyou.lucky.res.DailyreportRes;
import com.kuaiyou.lucky.service.ActiveService;
import com.kuaiyou.lucky.service.AdsenseService;
import com.kuaiyou.lucky.service.AdsenselogService;
import com.kuaiyou.lucky.service.DailyreportService;
import com.kuaiyou.lucky.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AdDataJob {

	@Autowired
	ActiveService activeService;

	@Autowired
	DailyreportService dailyreportService;

	@Autowired
	AdsenseService adsenseService;

	@Autowired
	AdsenselogService adsenselogService;

	@Scheduled(cron = "0 0/30 * * * ?")
	public void genReport() {
		excuteData(CoinTypeEnum.ADCLICK.getCode());
		excuteData(CoinTypeEnum.ADJUMP.getCode());
		excuteData(CoinTypeEnum.ADSHOW.getCode());
	}

	public void excuteData(Integer ctype) {
		ActiveRes req = new ActiveRes();
		req.setStarttime(DateUtil.getStartOfDay());
		req.setEndtime(DateUtil.getEndOfDay());
		req.setCtype(ctype);
		List<DailyreportRes> datas = activeService.dailyData(req);
		Date now = new Date();
		String today = DateUtil.format(now, "yyyy-MM-dd");
		for (DailyreportRes data : datas) {
			Dailyreport cache = dailyreportService.selectByProfile(today, data.getAsid(), data.getAcid());
			Adsenselog logcache = adsenselogService.selectByAcAs(data.getAcid(), data.getAsid());
			if (cache == null && logcache != null) {
				cache = new Dailyreport(today, data.getAcid(), data.getAsid(), logcache.getAcname(),
						logcache.getAsname(), new Integer(0), new Integer(0), new Integer(0), new Integer(0), now);
			}
			if (ctype.equals(CoinTypeEnum.ADCLICK.getCode())) {
				cache.setClick(data.getAmount());
				cache.setUclick(data.getUamount());
			} else if (ctype.equals(CoinTypeEnum.ADJUMP.getCode())) {
				cache.setJump(data.getAmount());
				cache.setUjump(data.getUamount());
			} else if (ctype.equals(CoinTypeEnum.ADSHOW.getCode())) {
				cache.setShow(data.getAmount());
				cache.setUshow(data.getUamount());
			}
			dailyreportService.insertOrUpdate(cache);
			log.info("{}", JSON.toJSONString(cache));
		}

	}

	public void excuteData(Integer ctype, String start, String end,String today) {
		ActiveRes req = new ActiveRes();
		req.setStarttime(start);
		req.setEndtime(end);
		req.setCtype(ctype);
		List<DailyreportRes> datas = activeService.dailyData(req);
		Date now = new Date();
		for (DailyreportRes data : datas) {
			Dailyreport cache = dailyreportService.selectByProfile(today, data.getAsid(), data.getAcid());
			Adsenselog logcache = adsenselogService.selectByAcAs(data.getAcid(), data.getAsid());
			if (cache == null && logcache != null) {
				cache = new Dailyreport(today, data.getAcid(), data.getAsid(), logcache.getAcname(),
						logcache.getAsname(), new Integer(0), new Integer(0), new Integer(0), new Integer(0), now);
			}
			if (ctype.equals(CoinTypeEnum.ADCLICK.getCode())) {
				cache.setClick(data.getAmount());
				cache.setUclick(data.getUamount());
			} else if (ctype.equals(CoinTypeEnum.ADJUMP.getCode())) {
				cache.setJump(data.getAmount());
				cache.setUjump(data.getUamount());
			} else if (ctype.equals(CoinTypeEnum.ADSHOW.getCode())) {
				cache.setShow(data.getAmount());
				cache.setUshow(data.getUamount());
			}
			dailyreportService.insertOrUpdate(cache);
			log.info("{}", JSON.toJSONString(cache));
		}

	}

}
