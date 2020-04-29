package com.kuaiyou.lucky.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Userdraw;
import com.kuaiyou.lucky.req.UserdrawReq;
import com.kuaiyou.lucky.res.AdminUserdrawRes;
import com.kuaiyou.lucky.res.DaydataRes;
import com.kuaiyou.lucky.res.DrawRes;
import com.kuaiyou.lucky.res.UserdrawRes;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
public interface UserdrawService extends IService<Userdraw> {

	boolean updateBatchWithLevel(ArrayList<Integer> eidList, int level, Integer prizetype);

	boolean insertDraw(UserdrawReq item);

	List<UserdrawRes> selectByDrawId(String drawid);

	List<UserdrawRes> selectByDrawId2(CommonReq req);

	List<Userdraw> selectDrawidWithUserid(List<Integer> collects, String userid);

	List<DrawRes> selectJoinList(CommonReq req);

	List<DrawRes> selectBingoList(CommonReq req);

	int selectJoinListCount(@Param("userid") String userid);

	int selectBingoListCount(@Param("userid") String userid);

	List<UserdrawRes> adminGrid(UserdrawReq item);

	int adminGridCount(UserdrawReq item);

	int selectByDrawId2Count(CommonReq req);

	List<DrawRes> selectPubList(CommonReq req);

	int selectPubListCount(String userid);

	List<DaydataRes> findUv(UserdrawReq item);

	List<DaydataRes> findPubcount(UserdrawReq item);

	List<DaydataRes> findOpencount(UserdrawReq item);

	List<DaydataRes> findCountjoin(UserdrawReq item);

	List<AdminUserdrawRes> adminGridDetail(UserdrawReq item);

	List<Userdraw> selectJoinsList(Integer drawid);

}
