package com.kuaiyou.lucky.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.SuperMapper;
import com.kuaiyou.lucky.entity.Userdraw;
import com.kuaiyou.lucky.req.UserdrawReq;
import com.kuaiyou.lucky.res.AdminUserdrawRes;
import com.kuaiyou.lucky.res.DaydataRes;
import com.kuaiyou.lucky.res.DrawRes;
import com.kuaiyou.lucky.res.UserdrawRes;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
public interface UserdrawMapper extends SuperMapper<Userdraw> {

	int updateBatchWithLevel(@Param("ids") ArrayList<Integer> eidList, @Param("level") int level,
			@Param("prizetype") Integer prizetype);

	List<UserdrawRes> selectByDrawId(@Param("drawid") String drawid);

	List<UserdrawRes> selectByDrawId2(CommonReq req);

	List<DrawRes> selectJoinList(CommonReq req);

	List<DrawRes> selectBingoList(CommonReq req);

	List<DrawRes> selectPubList(CommonReq req);

	Integer selectJoinListCount(@Param("userid") String userid);

	Integer selectBingoListCount(@Param("userid") String userid);

	Integer selectPubListCount(@Param("userid") String userid);

	List<UserdrawRes> adminGrid(UserdrawReq item);

	List<AdminUserdrawRes> adminGridDetail(UserdrawReq item);

	Integer adminGridCount(UserdrawReq item);

	Integer userDrawDetailCount(CommonReq req);

	Integer selectByDrawId2Count(CommonReq req);

	List<DaydataRes> findUv(UserdrawReq item);

	List<DaydataRes> findPubcount(UserdrawReq item);

	List<DaydataRes> findOpencount(UserdrawReq item);

	List<DaydataRes> findCountjoin(UserdrawReq item);

	List<Userdraw> selectJoinsList(@Param("drawid") Integer uwrapper);

}
