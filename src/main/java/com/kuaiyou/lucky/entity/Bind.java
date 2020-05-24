package com.kuaiyou.lucky.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2020-05-24
 */
@Data
@Accessors(chain = true)
@TableName("t_hxy_bind")
public class Bind extends Model<Bind> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String idcode;
    private String openid;


    public static final String ID = "id";

    public static final String IDCODE = "idcode";

    public static final String OPENID = "openid";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
