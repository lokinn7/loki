package com.kuaiyou.lucky.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 发布任务
 * </p>
 *
 * @author yardney
 * @since 2019-11-04
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_adviser")
public class Adviser extends Model<Adviser> {

    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 所属id(userid)busnessid
     */
    private Integer advid;
    /**
     * 状态0无效1有效
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 接入单价(分)
     */
    private Long price;
    /**
     * 任务简介
     */
    private String desc;
    /**
     * 更新时间
     */
    private Date utime;
    /**
     * 跳转类型 直接跳转:1  二维码跳转:2
     */
    private Integer jumptype;
    /**
     * 推广期开始
     */
    private Date starttime;
    /**
     * 推广期结束
     */
    private Date endtime;
    /**
     * 小程序所属类型1.渠道2.小游戏3.广告主
     */
    private Integer type;
    /**
     * appkey
     */
    private String appkey;
    /**
     * appsecret
     */
    private String appsecret;
    private String iconurl;
    private String inserturl;
    private String bannerurl;
    private String qrurl;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String ADVID = "advid";

    public static final String STATUS = "status";

    public static final String CTIME = "ctime";

    public static final String PRICE = "price";

    public static final String DESC = "desc";

    public static final String UTIME = "utime";

    public static final String JUMPTYPE = "jumptype";

    public static final String STARTTIME = "starttime";

    public static final String ENDTIME = "endtime";

    public static final String TYPE = "type";

    public static final String APPKEY = "appkey";

    public static final String APPSECRET = "appsecret";

    public static final String ICONURL = "iconurl";

    public static final String INSERTURL = "inserturl";

    public static final String BANNERURL = "bannerurl";

    public static final String QRURL = "qrurl";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
