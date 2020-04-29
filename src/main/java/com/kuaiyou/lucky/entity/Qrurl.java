package com.kuaiyou.lucky.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-08-20
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_qrurl")
public class Qrurl extends Model<Qrurl> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 计费链接
	 */
	private String linkurl;
	/**
	 * 创建时间
	 */
	private Date createtime;
	private String scene;
	private Integer drawid;
	private Integer goodsid;

	public static final String ID = "id";

	public static final String LINKURL = "linkurl";

	public static final String CREATETIME = "createtime";

	public static final String SCENE = "scene";

	public static final String DRAWID = "drawid";

	public static final String GOODSID = "goodsid";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Qrurl(String linkurl, Date createtime, String scene, Integer drawid, Integer goodsid) {
		this.drawid = drawid;
		this.linkurl = linkurl;
		this.createtime = createtime;
		this.scene = scene;
		this.goodsid = goodsid;
	}

	public Qrurl(String linkurl, Date createtime, String scene, Integer drawid) {
		this.drawid = drawid;
		this.linkurl = linkurl;
		this.createtime = createtime;
		this.scene = scene;
	}

	public Qrurl() {
		super();
	}

}
