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
package xxx.yyy.sys.rbac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import xxx.yyy.framework.common.enumeration.Gender;
import xxx.yyy.sys.base.model.BaseInfoModel;
import xxx.yyy.framework.common.utilities.BirthdayUtil;
import xxx.yyy.framework.common.utilities.CollectionUtils;
import xxx.yyy.framework.common.utilities.I18nUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by serv on 2014/5/31.
 */
@Entity
@Table(name = "SYS_RBAC_USER")
public class User extends BaseInfoModel {


    //登录密码
    @Column(name = "PASSWORD", nullable = false, length = 32)
    private String password;


    //邮件
    @Column(name = "EMAIL", length = 128)
    private String email;

    //用户所在的组
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_RBAC_ROLE_USER", joinColumns = {@JoinColumn(name = "SYS_RBAC_USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "SYS_RBAC_ROLE_ID")})
    private List<Role> roles = new ArrayList<Role>();

    //用户岗位列表
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_RBAC_POST_USER", joinColumns = {@JoinColumn(name = "SYS_RBAC_USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "SYS_RBAC_POST_ID")})
    private List<Post> posts = new ArrayList<Post>();
    /**
     * 生日
     */
    @Column(name = "BIRTH_DAY", nullable = true)
    private Date birthDay;
    /**
     * 是否为农历
     */
    @Column(name = "IS_LUNAR_CALENDAR")
    private boolean isLunarCalendar = false;

    /**
     * 入职日期
     */
    @Column(name = "ENTRY_DAY", nullable = true)
    private Date entryDay;


    /**
     * 工号
     */
    @Column(name = "USER_NO", length = 32)
    private String userNo;

    /**
     * 个人手机号
     */
    @Column(name = "MOBILE", length = 32)
    private String mobile;


    /**
     * 工作固话
     */
    @Column(name = "WORK_TEL", length = 32)
    private String workTel;

    /**
     * 家庭固话
     */
    @Column(name = "FAMILY_TEL", length = 32)
    private String familyTel;

    /**
     * 家庭地址
     */
    @Column(name = "FAMILY_ADDR", length = 128)
    private String familyAddr;

    /**
     * 传真
     */
    @Column(name = "FAX", length = 32)
    private String fax;

    /**
     * 用户性别
     */
    @Column(name = "GENDER")
    private Integer gender = Gender.MALE.getValue();


    /**
     * 所在部门 允许用户属于多个部门
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_RBAC_USER_DEPT", joinColumns = {@JoinColumn(name = "SYS_RBAC_USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "SYS_RBAC_DEPT_ID")})
    private List<Department> deptList = new ArrayList<Department>();

    /**
     * 所属默认部门
     */
    @Column(name = "DEFAULT_DEPT_ID", nullable = true)
    private String defaultDeptId;

    /**
     * 机密等级
     */
    @Column(name = "SECRET_LEVEL")
    private Integer secretLevel = 0;


    //用户头像
    @Column(name = "PORTRAIT", length = 254)
    private String portrait;

    //QQ号
    @Column(name = "QQ",length = 48)
    private String qq;

    //msn号
    @Column(name = "MSN",length = 48)
    private String msn;


    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录密码
     *
     * @param password 登录密码
     */
    public void setPassword(String password) {
        this.password = password;
    }




    /**
     * 获取邮件
     *
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮件
     *
     * @param email 邮件地址
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取该用户所在的组
     *
     * @return List
     */
    @JsonIgnore
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * 设置用户所在的组
     *
     * @param roles 组集合
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    /**
     * 获取所属角色的名称 多个名称 , 分隔
     *
     * @return String
     */
    @Transient
    @JsonIgnore
    public String getRoleNames() {
        return CollectionUtils.extractToString(this.roles, "name", ",");
    }

    /**
     * 获取用户头像
     *
     * @return String
     */
    public String getPortrait() {
        return portrait;
    }

    /**
     * 设置用户头像
     *
     * @param portrait 头像
     */
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }


    /**
     * 获取用户岗位列表
     *
     * @return the postList
     */
    @JsonIgnore
    public List<Post> getPosts() {
        return this.posts;
    }

    /**
     * 设置用户岗位列表
     *
     * @param posts the postList to set
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


    /**
     * @return the userNo
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * @param userNo the userNo to set
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the workTel
     */
    public String getWorkTel() {
        return workTel;
    }

    /**
     * @param workTel the workTel to set
     */
    public void setWorkTel(String workTel) {
        this.workTel = workTel;
    }

    /**
     * @return the familyTel
     */
    public String getFamilyTel() {
        return familyTel;
    }

    /**
     * @param familyTel the familyTel to set
     */
    public void setFamilyTel(String familyTel) {
        this.familyTel = familyTel;
    }

    /**
     * @return the familyAddr
     */
    public String getFamilyAddr() {
        return familyAddr;
    }

    /**
     * @param familyAddr the familyAddr to set
     */
    public void setFamilyAddr(String familyAddr) {
        this.familyAddr = familyAddr;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the gender
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * @return the secretLevel
     */
    public Integer getSecretLevel() {
        return secretLevel;
    }

    /**
     * @param secretLevel the secretLevel to set
     */
    public void setSecretLevel(Integer secretLevel) {
        this.secretLevel = secretLevel;
    }

    /**
     * @return the deptList
     */
    @JsonIgnore
    public List<Department> getDeptList() {
        return deptList;
    }

    /**
     * @param deptList the deptList to set
     */
    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }


    @Transient
    public String getGenderName() {
        return I18nUtils.getMessage(Gender.class, this.gender);
    }

    /**
     * @return the defaultDeptId
     */
    //TODO fetch
    public String getDefaultDeptId() {
        if(defaultDeptId==null){
            Department dept = deptList.get(0);
            defaultDeptId = dept.getId();
        }
        return defaultDeptId;
    }

    /**
     * @param defaultDeptId the defaultDept to set
     */
    public void setDefaultDeptId(String defaultDeptId) {
        this.defaultDeptId = defaultDeptId;
    }

    /**
     * @return the birthDay
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getBirthDay() {
        return birthDay;
    }

    /**
     * @param birthDay the birthDay to set
     */
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * @return the entryDay
     */
    public Date getEntryDay() {
        return entryDay;
    }

    /**
     * @param entryDay the entryDay to set
     */
    public void setEntryDay(Date entryDay) {
        this.entryDay = entryDay;
    }

    /**
     * @return the age
     */
    @Transient
    public Integer getAge() {
        if (this.birthDay == null) {
            return 0;
        } else {
            try {
                return BirthdayUtil.getAge(getBirthDay());
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public boolean getIsLunarCalendar() {
        return isLunarCalendar;
    }

    public void setIsLunarCalendar(boolean isLunarCalendar) {
        this.isLunarCalendar = isLunarCalendar;
    }

}
