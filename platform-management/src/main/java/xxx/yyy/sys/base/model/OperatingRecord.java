/**
 * Copyright (C) 2014 serv (liuyuhua69@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xxx.yyy.sys.base.model;

import xxx.yyy.framework.common.enumeration.OperatingState;
import xxx.yyy.framework.common.model.IdEntity;
import xxx.yyy.framework.common.utilities.I18nUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * 操作记录实体，记录用户的操作信息
 * 
 * @author serv
 *
 */
@Entity
@Table(name="SYS_OPERATING_RECORD")
public class OperatingRecord extends IdEntity{
	
	private static final long serialVersionUID = 1L;
	
	//操作人名称
	private String username;
	//操作人
	private String fkUserId;
	//操作开始时间
	private Date startDate;
	//操作结束时间
	private Date endDate;
	//操作目标
	private String operatingTarget;
	//ip地址
	private String ip;
	//操作的java方法
	private String method;
	//执行状态,1代表成，2代表执行时出现异常
	private Integer state;
	//模块名称
	private String module;
	//功能名称
	private String function;
	//描述
	private String remark;

    /**
	 * 构造方法
	 */
	public OperatingRecord() {
		
	}
	
	/**
	 * 获取操作人名称
	 * 
	 * @return String
	 */
	@Column(name="USERNAME",length=32)
	public String getUsername() {
		return username;
	}

	/**
	 * 设置操作人名称
	 * 
	 * @param username 操作人名称
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取操作人主键ID
	 * 
	 * @return String
	 */
	@Column(name="USER_ID",length=32)
	public String getFkUserId() {
		return fkUserId;
	}
	
	/**
	 * 设置操作人主键ID
	 * 
	 * @param fkUserId 操作人主键ID
	 */
	public void setFkUserId(String fkUserId) {
		this.fkUserId = fkUserId;
	}

	/**
	 * 获取操作开始时间
	 * 
	 * @return Date
	 */
	@Column(name="START_DATE",nullable=false)
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * 设置操作开始时间
	 * 
	 * @param startDate 操作开始时间
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 获取操作结束时间
	 * 
	 * @return Date
	 */
	@Column(name="END_DATE",nullable=false)
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置操作结束时间
	 * 
	 * @param endDate 操作结束时间
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取操作目标
	 * 
	 * @return String
	 */
	@Column(name="OPERATING_TARGET",length=512,nullable=false)
	public String getOperatingTarget() {
		return operatingTarget;
	}

	/**
	 * 设置操作目标
	 * 
	 * @param operatingTarget 操作目标
	 */
	public void setOperatingTarget(String operatingTarget) {
		this.operatingTarget = operatingTarget;
	}

	/**
	 * 获取id地址
	 * 
	 * @return String
	 */
	@Column(name="IP",length=64,nullable=false)
	public String getIp() {
		return ip;
	}

	/**
	 * 设置id地址
	 * 
	 * @param ip id地址
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取操作的java方法
	 * 
	 * @return String
	 */
	@Column(name="METHOD",length=256,nullable=false)
	public String getMethod() {
		return method;
	}

	/**
	 * 设置操作的java方法
	 * 
	 * @param method java方法名称
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 获取执行状态,1代表成，2代表执行时出现异常
	 * 
	 * @return Integer
	 */
	@Column(name="STATE",nullable=false)
	public Integer getState() {
		return state;
	}

	/**
	 * 设置执行状态
	 * @param state 1代表成，2代表执行时出现异常
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	
	/**
	 * 获取模块名称
	 * 
	 * @return String
	 */
	@Column(name="MODULE",length=128)
	public String getModule() {
		return module;
	}

	/**
	 * 设置模块名称
	 * 
	 * @param module 模块名称
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * 获取功能名称
	 * 
	 * @return String
	 */
	@Column(name="FUNCTION",length=128)
	public String getFunction() {
		return function;
	}

	/**
	 * 设置功能名称
	 * 
	 * @param function 功能名称
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * 获取描述
	 * 
	 * @return String
	 */
	@Lob
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置描述
	 * 
	 * @param remark 描述
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 获取状态的中文名称
	 * 
	 * @return String
	 */
	@Transient
	public String getStateName() {
		return I18nUtils.getMessage(OperatingState.class, this.state);
	}
	
}
