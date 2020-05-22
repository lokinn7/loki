package com.kuaiyou.lucky.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableLogic;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 微信用户数据库
 * </p>
 *
 * @author yardney
 * @since 2019-09-25
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_openuser")
public class Openuser extends Model<Openuser> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增索引
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 微信OPENID
     */
    private String openid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 是否关注0-否，1-是
     */
    private Integer sub;
    /**
     * UNKNOWN(0), MALE(1), FEMALE(2)

     */
    private Integer gender;
    /**
     * 城市
     */
    private String city;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 语言
     */
    private String language;
    /**
     * 头像URL
     */
    private String avatarurl;
    /**
     * 取消关注时间
     */
    private Date unsubtime;
    /**
     * 关注时间
     */
    private Date subtime;
    private String unionid;
    /**
     * 标签
     */
    private String remark;
    /**
     * 组ID
     */
    private Integer groupid;
    /**
     * 标签
     */
    private String tags;
    private Date ctime;
    @TableLogic
    private Integer deleted;


    public static final String ID = "id";

    public static final String OPENID = "openid";

    public static final String NICKNAME = "nickname";

    public static final String SUB = "sub";

    public static final String GENDER = "gender";

    public static final String CITY = "city";

    public static final String COUNTRY = "country";

    public static final String PROVINCE = "province";

    public static final String LANGUAGE = "language";

    public static final String AVATARURL = "avatarurl";

    public static final String UNSUBTIME = "unsubtime";

    public static final String SUBTIME = "subtime";

    public static final String UNIONID = "unionid";

    public static final String REMARK = "remark";

    public static final String GROUPID = "groupid";

    public static final String TAGS = "tags";

    public static final String CTIME = "ctime";

    public static final String DELETED = "deleted";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
