package com.kuaiyou.lucky.compnent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.entity.Convert;
import com.kuaiyou.lucky.entity.Goods;
import com.kuaiyou.lucky.entity.jdentity.GoodInfoRes;
import com.kuaiyou.lucky.service.ConvertService;
import com.kuaiyou.lucky.service.GoodsService;
import com.kuaiyou.lucky.utils.JsonUtil;

import jd.union.open.coupon.query.request.UnionOpenCouponQueryRequest;
import jd.union.open.coupon.query.response.UnionOpenCouponQueryResponse;
import jd.union.open.goods.promotiongoodsinfo.query.request.UnionOpenGoodsPromotiongoodsinfoQueryRequest;
import jd.union.open.goods.promotiongoodsinfo.query.response.PromotionGoodsResp;
import jd.union.open.goods.promotiongoodsinfo.query.response.UnionOpenGoodsPromotiongoodsinfoQueryResponse;
import jd.union.open.goods.query.request.GoodsReq;
import jd.union.open.goods.query.request.UnionOpenGoodsQueryRequest;
import jd.union.open.goods.query.response.Coupon;
import jd.union.open.goods.query.response.GoodsResp;
import jd.union.open.goods.query.response.ImageInfo;
import jd.union.open.goods.query.response.PriceInfo;
import jd.union.open.goods.query.response.ShopInfo;
import jd.union.open.goods.query.response.UnionOpenGoodsQueryResponse;
import jd.union.open.goods.query.response.UrlInfo;
import jd.union.open.promotion.applet.get.request.PromotionCodeAppletReq;
import jd.union.open.promotion.applet.get.request.UnionOpenPromotionAppletGetRequest;
import jd.union.open.promotion.applet.get.response.UnionOpenPromotionAppletGetResponse;
import jd.union.open.promotion.bysubunionid.get.request.PromotionCodeReq;
import jd.union.open.promotion.bysubunionid.get.request.UnionOpenPromotionBysubunionidGetRequest;
import jd.union.open.promotion.bysubunionid.get.response.UnionOpenPromotionBysubunionidGetResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JDCompnent {

	@Autowired
	Project project;

	@Autowired
	GoodsService goodsService;

	@Autowired
	ConvertService convertService;

	public final static String UNIONPATH = "/pages/proxy/union/union?spreadUrl=%s&customerinfo=%s";
	public final static String SERVERURL = "https://router.jd.com/api";

	// public void excuteOffline() {
	// /**
	// * 查看当前id的优惠券状态
	// */
	// EntityWrapper<Goods> wrapper = new EntityWrapper<>();
	// wrapper.eq(Goods.DELETED, 1);
	// List<Goods> goods = goodsService.selectList(wrapper);
	// for (Goods temp : goods) {
	//
	// }
	// }

	// 查询优惠券具体信息
	public void getCouponInfo(String[] urls) {
		JdClient client = new DefaultJdClient(SERVERURL, null, project.getJdkey(), project.getJdopensecret());
		UnionOpenCouponQueryRequest request = new UnionOpenCouponQueryRequest();
		request.setCouponUrls(urls);
		try {
			UnionOpenCouponQueryResponse res = client.execute(request);
			System.out.println(JSON.toJSONString(res));
		} catch (JdException e) {
			e.printStackTrace();
		}
	}

	// 查询优惠券信息
	public void getAllCoupon() {
		JdClient client = new DefaultJdClient(SERVERURL, null, project.getJdkey(), project.getJdopensecret());
		UnionOpenPromotionAppletGetRequest request = new UnionOpenPromotionAppletGetRequest();
		PromotionCodeAppletReq promotionCodeReq = new PromotionCodeAppletReq();
		request.setPromotionCodeReq(promotionCodeReq);
		try {
			UnionOpenPromotionAppletGetResponse res = client.execute(request);
			// TODO
			log.info("{}", JSON.toJSONString(res));
		} catch (JdException e) {
			e.printStackTrace();
			log.error("{}", e);
		}
	}

	/**
	 * <pre>
	 * 		通过商品链接、领券链接、活动链接获取普通推广链接或优惠券二合一推广链接，支持传入subunionid参数，
	 * 可用于区分媒体自身的用户ID。需向cps-qxsq jd.com申请权限。功能同宙斯接口的优惠券,商品二合一转接API-通过subUnionId获取推广链接、
	 * 联盟微信手q通过subUnionId获取推广链接。
	 * </pre>
	 */
	public String getPubShorLink(String materiaid, String couponurl) {
		JdClient client = new DefaultJdClient(SERVERURL, null, project.getJdkey(), project.getJdopensecret());
		UnionOpenPromotionBysubunionidGetRequest request = new UnionOpenPromotionBysubunionidGetRequest();
		PromotionCodeReq promotionCodeReq = new PromotionCodeReq();
		promotionCodeReq.setMaterialId(materiaid);
		if (StringUtils.isNotBlank(couponurl)) {
			promotionCodeReq.setCouponUrl(couponurl);
		}
		request.setPromotionCodeReq(promotionCodeReq);
		try {
			UnionOpenPromotionBysubunionidGetResponse res = client.execute(request);
			log.info("{}", JSON.toJSONString(res));
			return res.getData().getShortURL();
		} catch (JdException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getPubShorLink(String materiaid) {
		JdClient client = new DefaultJdClient(SERVERURL, null, project.getJdkey(), project.getJdopensecret());
		UnionOpenPromotionBysubunionidGetRequest request = new UnionOpenPromotionBysubunionidGetRequest();
		PromotionCodeReq promotionCodeReq = new PromotionCodeReq();
		promotionCodeReq.setMaterialId(materiaid);
		request.setPromotionCodeReq(promotionCodeReq);
		try {
			UnionOpenPromotionBysubunionidGetResponse res = client.execute(request);
			log.info("{}", JSON.toJSONString(res));
			return res.getData().getShortURL();
		} catch (JdException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据skuid查询商品详情,skuid按,逗号分隔,最多100个
	 * 
	 * @return
	 */
	public List<PromotionGoodsResp> getGoodsInfoByString(String skuids) {
		JdClient client = new DefaultJdClient(SERVERURL, null, project.getJdkey(), project.getJdopensecret());
		UnionOpenGoodsPromotiongoodsinfoQueryRequest request = new UnionOpenGoodsPromotiongoodsinfoQueryRequest();
		request.setSkuIds(skuids);
		try {
			UnionOpenGoodsPromotiongoodsinfoQueryResponse res = client.execute(request);
			PromotionGoodsResp[] data = res.getData();
			log.info("{}", (JSON.toJSONString(res)));
			return Arrays.asList(data);
		} catch (JdException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 按键入条件查询商品集合
	public List<GoodsResp> getGoodsInfoByIds(Long[] skuids) {
		JdClient client = new DefaultJdClient(SERVERURL, null, project.getJdkey(), project.getJdopensecret());
		UnionOpenGoodsQueryRequest request = new UnionOpenGoodsQueryRequest();
		GoodsReq goodsReqDTO = new GoodsReq();
		goodsReqDTO.setSkuIds(skuids);
		request.setGoodsReqDTO(goodsReqDTO);
		try {
			UnionOpenGoodsQueryResponse res = client.execute(request);
			GoodsResp[] data = res.getData();
			log.info("{}", (JSON.toJSONString(res)));
			return Arrays.asList(data);
		} catch (JdException e) {
			e.printStackTrace();
		}
		return null;
	}

	public GoodsResp getGoodsInfoBySkuid(Long skuid) {
		JdClient client = new DefaultJdClient(SERVERURL, null, project.getJdkey(), project.getJdopensecret());
		UnionOpenGoodsQueryRequest request = new UnionOpenGoodsQueryRequest();
		GoodsReq goodsReqDTO = new GoodsReq();
		goodsReqDTO.setSkuIds(new Long[] { skuid });
		request.setGoodsReqDTO(goodsReqDTO);
		try {
			UnionOpenGoodsQueryResponse res = client.execute(request);
			GoodsResp[] data = res.getData();
			log.info("{}", (JSON.toJSONString(res)));
			return data[0];
		} catch (JdException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<GoodInfoRes> detail(Integer type, String userid) {
		List<GoodInfoRes> results = new ArrayList<>();
		/**
		 * <pre>
		 * 		-1.从数据库中获取商品skuids
		 * 		0.从京东后台获取skuids、推广链接、优惠券链接  x 无法一一对应
		 *  	1.根据skuids获取商品列表,附带详情,
		 *  	2.获取商品详情
		 * </pre>
		 */
		List<Convert> joins = convertService.selectByUserid(userid);
		List<Long> skuids = goodsService.selectByType(type);
		if (skuids.size() > 0) {

			List<GoodsResp> res1 = getGoodsInfoByIds(skuids.toArray(new Long[skuids.size()]));
			for (GoodsResp goodsResp : res1) {
				Coupon[] couponList = goodsResp.getCouponInfo().getCouponList();
				String couponurl = null;
				for (Coupon coupon : couponList) {
					couponurl = coupon.getLink();
					if (StringUtils.isNotBlank(couponurl)) {
						break;
					}
				}
				String link = getPubShorLink(goodsResp.getMaterialUrl(), couponurl);
				String encode = null;
				try {
					encode = URLEncoder.encode(link, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				GoodInfoRes res = new GoodInfoRes();
				BeanUtils.copyProperties(goodsResp, res);
				res.setPuburl(String.format(UNIONPATH, encode, project.getCustomerinfo()));
				joins.forEach(e -> {
					if (e.getSkuid().equals(goodsResp.getSkuId())) {
						res.setAljoin(1);
					}
				});
				results.add(res);
			}
		}
		return results;
	}

	public boolean insertGoods(List<Goods> goodes) {
		/**
		 * <pre>
		 * 		0.从数据库中获取商品skuids
		 *  	1.根据skuids获取商品列表,附带详情,
		 *  	2.拼装入库
		 * </pre>
		 */
		List<Goods> addbatches = new ArrayList<>();
		Date now = new Date();
		for (Goods goods : goodes) {
			GoodsResp goodsResp = getGoodsInfoBySkuid(goods.getSkuid());
			if (goodsResp != null) {
				Coupon[] couponList = goodsResp.getCouponInfo().getCouponList();
				String couponurl = null;
				for (Coupon coupon : couponList) {
					couponurl = coupon.getLink();
					Double discount = coupon.getDiscount();
					if (StringUtils.isNotBlank(couponurl)) {
						goods.setDiscount(discount);
						break;
					}
				}
				String link = getPubShorLink(goodsResp.getMaterialUrl(), couponurl);
				String encode = null;
				try {
					encode = URLEncoder.encode(link, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				ImageInfo imageInfo = goodsResp.getImageInfo();
				UrlInfo[] imageList = imageInfo.getImageList();

				PriceInfo priceInfo = goodsResp.getPriceInfo();
				ShopInfo shopInfo = goodsResp.getShopInfo();
				goods.setCouponurl(String.format(UNIONPATH, encode, project.getCustomerinfo()));
				goods.setCouponlink(couponurl);
				goods.setBrandname(goodsResp.getBrandName());
				goods.setImgurl(imageList[0].getUrl());
				goods.setIsjdale(goodsResp.getIsJdSale());
				goods.setPrice(priceInfo.getPrice());
				goods.setLowestprice(priceInfo.getPrice() - (goods.getDiscount() == null ? 0 : goods.getDiscount()));
				goods.setShopname(shopInfo.getShopName());
				goods.setSkuid(goodsResp.getSkuId());
				goods.setSkuname(goodsResp.getSkuName());
				goods.setCtime(now);
			}
			addbatches.add(goods);
		}
		return goodsService.insertBatch(addbatches);
	}

	public boolean insertGood(Goods goods) {
		/**
		 * <pre>
		 * 		0.从数据库中获取商品skuids
		 *  	1.根据skuids获取商品列表,附带详情,
		 *  	2.拼装入库
		 * </pre>
		 */
		Date now = new Date();
		GoodsResp goodsResp = getGoodsInfoBySkuid(goods.getSkuid());
		if (goodsResp != null) {
			String couponurl = null;
			if (goods.getGtype().equals(2)) {
				Coupon[] couponList = goodsResp.getCouponInfo().getCouponList();
				for (Coupon coupon : couponList) {
					couponurl = coupon.getLink();
					Double discount = coupon.getDiscount();
					if (StringUtils.isNotBlank(couponurl)) {
						goods.setDiscount(discount);
						break;
					}
				}
			}
			String link = getPubShorLink(goodsResp.getMaterialUrl(), couponurl);
			String encode = null;
			try {
				encode = URLEncoder.encode(link, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			ImageInfo imageInfo = goodsResp.getImageInfo();
			UrlInfo[] imageList = imageInfo.getImageList();

			PriceInfo priceInfo = goodsResp.getPriceInfo();
			ShopInfo shopInfo = goodsResp.getShopInfo();
			goods.setCouponurl(String.format(UNIONPATH, encode, project.getCustomerinfo()));
			goods.setCouponlink(couponurl);
			goods.setBrandname(goodsResp.getBrandName());
			goods.setImgurl(imageList[0].getUrl());
			goods.setIsjdale(goodsResp.getIsJdSale());
			goods.setLowestprice(priceInfo.getPrice() - (goods.getDiscount() == null ? 0 : goods.getDiscount()));
			goods.setPrice(priceInfo.getPrice());
			goods.setShopname(shopInfo.getShopName());
			goods.setSkuid(goodsResp.getSkuId());
			goods.setSkuname(goodsResp.getSkuName());
			goods.setCtime(now);
			goods.setShareimg(goods.getShareimg());
			return goodsService.insert(goods);
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(JsonUtil.json2String(new GoodsResp()));
	}

}
