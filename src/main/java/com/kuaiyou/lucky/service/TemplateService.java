package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.entity.Template;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-08-14
 */
public interface TemplateService extends IService<Template> {

	boolean insertBase(String userid, String formid, Integer drawid);

	List<Template> selectListByType(Integer id, int code);

}
