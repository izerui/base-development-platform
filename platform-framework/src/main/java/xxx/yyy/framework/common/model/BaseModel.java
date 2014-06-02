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
package xxx.yyy.framework.common.model;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;


/**
 * 
 * 模型基类
 * @author serv
 *
 */
@MappedSuperclass
public abstract class BaseModel extends IdEntity{
 

    @Version
    protected int version;

	//单位机构
    @Column(name="ORG_ID")
	protected String orgId ;
 
	
	//删除状态
    @Column(name="DELETE_STATUS",nullable=false)
	protected boolean deleteStatus = Boolean.FALSE;


	//默认排序值
    @Column(name="SORT",nullable=false)
	protected Long sort  = 0L;
	
	
	
	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public boolean isDeleteStatus() {
		return deleteStatus;
	}


	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	
	
 	
	public Long getSort() {
		return sort;
	}


	public void setSort(Long sort) {
		this.sort = sort;
	}
	

}
