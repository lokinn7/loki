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
 * 媒体表
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_business")
public class Business extends Model<Business> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 公司名称
     */
    private String company;
    /**
     * 商务种类1渠道2小游戏3广告主
     */
    private Integer type;
    /**
     * 商务标识，渠道小游戏有
     */
    private String flag;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 手机号
     */
    private String phone;
    /**
     * qq
     */
    private String qq;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 1有效0无效
     */
    private Integer status;
    /**
     * 更新时间
     */
    private Date utime;
    /**
     * 创建时间
     */
    private Date ctime;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String COMPANY = "company";

    public static final String TYPE = "type";

    public static final String FLAG = "flag";

    public static final String CONTACTS = "contacts";

    public static final String PHONE = "phone";

    public static final String QQ = "qq";

    public static final String EMAIL = "email";

    public static final String ADDRESS = "address";

    public static final String STATUS = "status";

    public static final String UTIME = "utime";

    public static final String CTIME = "ctime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
