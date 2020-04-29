package com.kuaiyou.lucky.entity;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户设备表
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_userdev")
public class Userdev extends Model<Userdev> {

    private static final long serialVersionUID = 1L;

    /**
     * openid
     */
    private String userid;
    private String openid;
    /**
     * 微信版本
     */
    private String wechatversion;
    /**
     * 设别像素比
     */
    private String pixelratio;
    /**
     * 系统版本
     */
    private String sysversion;
    /**
     * 客户端平台
     */
    private String phoneplatform;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 网络 4g
     */
    private String network;
    /**
     * 手机品牌
     */
    private String mobilebrand;
    /**
     * 经纬度
     */
    private String lngandlat;
    /**
     * 客户端语言
     */
    private String lang;
    /**
     * 窗口宽度
     */
    private Integer width;
    /**
     * 屏幕高度
     */
    private Integer height;


    public static final String USERID = "userid";

    public static final String OPENID = "openid";

    public static final String WECHATVERSION = "wechatversion";

    public static final String PIXELRATIO = "pixelratio";

    public static final String SYSVERSION = "sysversion";

    public static final String PHONEPLATFORM = "phoneplatform";

    public static final String OS = "os";

    public static final String CTIME = "ctime";

    public static final String NETWORK = "network";

    public static final String MOBILEBRAND = "mobilebrand";

    public static final String LNGANDLAT = "lngandlat";

    public static final String LANG = "lang";

    public static final String WIDTH = "width";

    public static final String HEIGHT = "height";

    @Override
    protected Serializable pkVal() {
        return this.userid;
    }

}
