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
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-08-16
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_address")
public class Address extends Model<Address> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Date ctime;
    private String userid;
    private String address;
    private String drawid;


    public static final String ID = "id";

    public static final String CTIME = "ctime";

    public static final String USERID = "userid";

    public static final String ADDRESS = "address";
    
    public static final String DRAWID = "drawid";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
