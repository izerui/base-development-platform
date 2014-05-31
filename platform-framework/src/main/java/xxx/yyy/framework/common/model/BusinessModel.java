package xxx.yyy.framework.common.model;

import xxx.yyy.framework.common.enumeration.BusinessStatus;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by serv on 14-5-30.
 */
@MappedSuperclass
public abstract class BusinessModel extends BaseModel{

    
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
