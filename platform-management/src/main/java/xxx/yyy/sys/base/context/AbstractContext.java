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
package xxx.yyy.sys.base.context;

import com.google.common.collect.Lists;
import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.framework.common.enumeration.DepartmentType;
import xxx.yyy.sys.datafilter.service.FilterRuleService;
import xxx.yyy.sys.rbac.model.Department;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.rbac.service.AccountService;
import xxx.yyy.sys.rbac.service.DepartmentService;
import xxx.yyy.sys.rbac.service.PostService;
import xxx.yyy.sys.rbac.service.ResourceService;

import java.io.Serializable;
import java.util.List;

import static xxx.yyy.framework.common.utilities.CollectionUtils.extractToList;

/**
 * Created by serv on 14-6-3.
 */
public abstract class AbstractContext implements Serializable,UserContext,RoleContext,PostContext,DepartmentContext,ResourceContext,FilterRuleContext{

    private final static String IGNORE_DEPT_GROUP = DepartmentType.DEPARTMENT.getValue()+","+DepartmentType.GROUP.getValue();

    private final static String IGNORE_ORG_GROUP = DepartmentType.ORGANIZATION.getValue()+","+DepartmentType.GROUP.getValue();

    private final static String SYSTEM_SUPER_ADMIN_ROLE_NAME = "system";

    // 当前用户
    private final User user;


    protected transient DepartmentService departmentService;
    protected transient AccountService accountService;
    protected transient ResourceService resourceService;
    protected transient PostService postService;
    protected transient FilterRuleService filterRuleService;

    public AbstractContext(User user) {
        this.user = user;
        departmentService = SpringContextHolder.getBean(DepartmentService.class);
        accountService = SpringContextHolder.getBean(AccountService.class);
        resourceService = SpringContextHolder.getBean(ResourceService.class);
        postService = SpringContextHolder.getBean(PostService.class);
        filterRuleService = SpringContextHolder.getBean(FilterRuleService.class);
    }

    @Override
    public User getUser() {
        //本地机器环境，或者测试环境下使用
        if(null==user){
            return ThreadLocalUserContext.getUser();
        }
        return user;
    }

    /**
     * 获取department 列表
     * @param deptId departmentId
     * @param containSelf 是否包含当前department
     * @param ignoreTypes 忽略的类型 , 多个以 , 逗号区分
     * @return list of {@link xxx.yyy.sys.rbac.model.Department}
     */
    private List<Department> getChildDepartments(String deptId,boolean containSelf,String ignoreTypes) {
        List<Department> departments = departmentService.findOne(getUserOrgId()).mergerTolist(containSelf,ignoreTypes);;
        return departments;
    }



    @Override
    public String getUserOrgId() {
        return getUser().getOrgId();
    }

    @Override
    public Department getUserOrg() {
        return departmentService.findOne(this.getUserOrgId());
    }

    @Override
    public Department getUserRootOrg() {
        return departmentService.findRootDept(this.getUserOrgId());
    }

    @Override
    public String getUserRootOrgId() {
        return this.getUserRootOrg().getId();
    }

    @Override
    public Department getUserDefaultDept() {
        return departmentService.findOne(getUser().getDefaultDeptId());
    }

    @Override
    public String getUserDefaultDeptId() {
        return getUser().getDefaultDeptId();
    }

    @Override
    public List<Department> getUserChildOrgs(boolean containSelf) {
        return getChildDepartments(getUserOrgId(),containSelf,IGNORE_DEPT_GROUP);
    }

    @Override
    public List<String> getDepartmentIds() {
        return extractToList(this.getDepartmentList(), "id");
    }

    @Override
    public List<String> getAllChildDepartmentIds(boolean containSelf) {
        return extractToList(getAllChildDepartments(containSelf), "id");
    }

    @Override
    public List<Department> getAllChildDepartments(boolean containSelf) {
        List<Department> all = Lists.newArrayList();
        for(Department dept:getDepartmentList()){
            all.addAll(getChildDepartments(dept.getId(),containSelf,IGNORE_ORG_GROUP));
        }
        return all;
    }

    @Override
    public List<String> getUserChildOrgIds(boolean containSelf) {
        return extractToList(this.getUserChildOrgs(containSelf), "id") ;
    }

    @Override
    public List<String> getUserParentDeptIds(boolean containSelf) {
        return extractToList(this.getUserParentDepts(containSelf), "id") ;
    }

    @Override
    public List<Department> getUserParentDepts(boolean containSelf) {

        List<Department> allParents = Lists.newArrayList();

        for(Department department: getDepartmentList()){
            allParents.addAll(departmentService.getAllParent(department.getId(),containSelf,IGNORE_ORG_GROUP));
        }
        return allParents;
    }

    @Override
    public List<String> getGroupIds() {
        return extractToList(getGroupList(), "id");
    }

    @Override
    public List<String> getPostIds() {
        return extractToList(getPostList(), "id") ;
    }

    @Override
    public boolean checkRole(String role) {
        return org.springframework.util.CollectionUtils.contains(this.geRoleNames().iterator(), role);
    }

    @Override
    public List<String> geRoleNames() {
        return extractToList(this.getRolesList(), "name");
    }

    @Override
    public List<String> getRoleIds() {
        return extractToList(this.getRolesList(), "id");
    }

    @Override
    public boolean isSystem() {
        return this.checkRole(SYSTEM_SUPER_ADMIN_ROLE_NAME);
    }
}
