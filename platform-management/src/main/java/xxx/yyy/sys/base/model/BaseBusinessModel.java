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

import org.joda.time.DateTime;
import xxx.yyy.framework.common.enumeration.BusinessStatus;
import xxx.yyy.sys.base.context.SessionVariable;
import xxx.yyy.sys.base.context.SystemContextHolder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Created by serv on 14-5-30.
 */
@MappedSuperclass
public abstract class BaseBusinessModel extends BaseModel{

    
    //创建者
    @Column(name="CREATE_USER_ID",length=32,nullable=false)
    protected String creatorUserId ;

    //修改者
    @Column(name = "AUDIT_USER_ID",length = 32)
    protected String auditUserId;

    //更新时间
    @Column(name = "AUDIT_DATE")
    protected Date auditDate;


    //所属部门
    @Column(name="DEPT_ID",length=32,nullable=false)
    protected String deptId ;


    //业务状态
    @Column(name="BUSINESS_STATUS",nullable=false)
    protected  Integer  businessStatus =  BusinessStatus.DRAFT.getValue();


    //创建时间
    @Column(name = "CREATE_DATE", updatable = false)
    protected Date createDate =  null ;

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
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

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    @PreUpdate
    public void updateAuditInfo() {
        setAuditUserId(SystemContextHolder.getSessionContext().getUserId());
        setAuditDate(new DateTime().toDate());
    }

    @PrePersist
    public void applyCreateInfo(){
        SessionVariable sessionVariable = SystemContextHolder.getSessionContext();
        setOrgId(sessionVariable.getOrgContext().getOrgId());
        setDeptId(sessionVariable.getUser().getDefaultDeptId());
        setCreateDate(new DateTime().toDate());
        setCreatorUserId(sessionVariable.getUserId());
    }

}
