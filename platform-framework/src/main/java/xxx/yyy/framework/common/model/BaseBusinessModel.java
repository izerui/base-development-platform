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

import xxx.yyy.framework.common.enumeration.BusinessStatus;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by serv on 14-5-30.
 */
@MappedSuperclass
public abstract class BaseBusinessModel extends BaseModel{

    
    //创建者
    @Column(name="CREATE_ID",length=32,nullable=false)
    protected String creatorId ;


    //所属部门
    @Column(name="DEPT_ID",length=32,nullable=false)
    protected String deptId ;


    //业务状态
    @Column(name="BUSINESS_STATUS",nullable=false)
    protected  Integer  businessStatus =  BusinessStatus.DRAFT.getValue();


    //创建时间
    @Column(name = "CREATE_DATE", updatable = false)
    protected Date createDate =  null ;

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Integer getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(Integer businessStatus) {
        this.businessStatus = businessStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
