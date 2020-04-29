package com.kuaiyou.lucky.entity;

import com.baomidou.mybatisplus.annotations.TableField;
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
 * @since 2020-04-29
 */
@Data
@Accessors(chain = true)
@TableName("t_hxy_salary")
public class Salary extends Model<Salary> {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nickname;
	private String department;
	private String bankcode;
	private String job;
	private Double postcode;
	private Double coefficient;
	@TableField("post_salary")
	private Double postSalary;
	@TableField("base_salary")
	private Double baseSalary;
	@TableField("post_subsidy")
	private Double postSubsidy;
	@TableField("edu_subsidy")
	private String eduSubsidy;
	@TableField("overtime_salary")
	private String overtimeSalary;
	private Double bonus;
	private Double attendance;
	private String overtime;
	private Double salary;
	@TableField("other_subsidy")
	private Double otherSubsidy;
	private String fine;
	private Double total;
	@TableField("add_taxes")
	private Double addTaxes;
	private String mines;
	private String taxes;
	@TableField("fact_salary")
	private Double factSalary;
	private String gender;
	private String phone;
	private String injobdate;
	private String idcode;
	private String month;

	public static final String ID = "id";

	public static final String NICKNAME = "nickname";

	public static final String DEPARTMENT = "department";

	public static final String BANKCODE = "bankcode";

	public static final String JOB = "job";

	public static final String POSTCODE = "postcode";

	public static final String COEFFICIENT = "coefficient";

	public static final String POST_SALARY = "post_salary";

	public static final String BASE_SALARY = "base_salary";

	public static final String POST_SUBSIDY = "post_subsidy";

	public static final String EDU_SUBSIDY = "edu_subsidy";

	public static final String OVERTIME_SALARY = "overtime_salary";

	public static final String BONUS = "bonus";

	public static final String ATTENDANCE = "attendance";

	public static final String OVERTIME = "overtime";

	public static final String SALARY = "salary";

	public static final String OTHER_SUBSIDY = "other_subsidy";

	public static final String FINE = "fine";

	public static final String TOTAL = "total";

	public static final String ADD_TAXES = "add_taxes";

	public static final String MINES = "mines";

	public static final String TAXES = "taxes";

	public static final String FACT_SALARY = "fact_salary";

	public static final String GENDER = "gender";

	public static final String PHONE = "phone";

	public static final String INJOBDATE = "injobdate";

	public static final String MONTH = "month";
	
	public static final String IDCODE = "idcode";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
