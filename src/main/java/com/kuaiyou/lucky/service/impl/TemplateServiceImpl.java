package com.kuaiyou.lucky.service.impl;

import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.entity.Template;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.enums.TemplateTypeEnum;
import com.kuaiyou.lucky.mapper.TemplateMapper;
import com.kuaiyou.lucky.service.TemplateService;
import com.kuaiyou.lucky.service.WxuserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-08-14
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {

	@Autowired
	WxuserService wxuserService;

	@Autowired
	Project project;

	@Override
	public boolean insertBase(String userid, String formid, Integer drawid) {
		Wxuser user = wxuserService.selectByUserid(userid);
		Template template = new Template();
		template.setCtime(new Date());
		template.setDrawid(drawid);
		template.setFormid(formid);
		template.setOpenid(user.getOpenid());
		template.setPage("/pages/prizedetail/prizedetail?source=notify&drawid=" + drawid);
		template.setStatus(1);
		template.setTemplateid(project.getModel1());
		template.setUserid(userid);
		template.setType(TemplateTypeEnum.OPENMINI.getCode());
		return insert(template);
	}

	@Override
	public List<Template> selectListByType(Integer id, int code) {
		EntityWrapper<Template> wrapper = new EntityWrapper<>();
		wrapper.eq(Template.DRAWID, id).eq(Template.STATUS, 1).eq(Template.TYPE, code);
		return selectList(wrapper);
	}
}
