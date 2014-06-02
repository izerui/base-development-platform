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
package xxx.yyy.sys.security;

import com.google.common.collect.Lists;
import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.framework.common.enumeration.DepartmentType;
import xxx.yyy.sys.security.model.*;
import xxx.yyy.sys.security.service.DepartmentService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static xxx.yyy.framework.common.utilities.CollectionUtils.extractToList;

/**
 * 
 * 系统常用变量模型实体
 * 
 * @author izerui.com
 * 
 */
public class SessionVariable implements Serializable ,UserContext,RoleContext,SecretContext,PostContext,DepartmentContext,ResourceContext {


    private static final String SYSTEM_SUPER_ADMIN_ROLE_NAME = "system";

    // 当前用户
	private User user;

	// 当前用户所在的角色集合
	private List<Role> rolesList;

	// 当前用户的授权资源集合
	private List<Resource> authorizationInfo;

	// 菜单树
	private List<Resource> menusList;

	//包含所有类型的安全树
	private List<Resource> resourceList;

    private List<Department> departmentList;


	//TODO 用户数据权限规则
//	private List<SecretRule> secretRuleList ;


    //岗位列表
	private List<Post> postList  ;


	public SessionVariable() {

	}

	public SessionVariable(User user) {
		this.user = user;
	}

	public SessionVariable(User user, List<Role> rolesList, List<Resource> authorizationInfo, List<Resource> menusList,List<Department> departmentList) {
		this.user = user;
		this.rolesList = rolesList;
		this.authorizationInfo = authorizationInfo;
		this.menusList = menusList;
        this.departmentList = departmentList;
	}

	/**
	 * 获取当前用户
	 *
	 * @return {@link User}
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 设置当前用户
	 *
	 * @param user 当前用户
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 获取当前用户所在的角色集合
	 *
	 * @return List
	 */
	public List<Role> getRolesList() {
		return rolesList;
	}

	/**
	 * 设置当前用户所在的组集合
	 *
	 * @param rolesList 组集合
	 */
	public void setRolesList(List<Role> rolesList) {
		this.rolesList = rolesList;
	}

	/**
	 * 获取当前用户的所有授权资源集合
	 *
	 * @return List
	 */
	public List<Resource> getAuthorizationInfo() {
		return authorizationInfo;
	}

	/**
	 * 设置当前用户的所有授权资源集合
	 *
	 * @param authorizationInfo 资源集合
	 */
	public void setAuthorizationInfo(List<Resource> authorizationInfo) {
		this.authorizationInfo = authorizationInfo;
	}

	/**
	 * 获取当前用户拥有的菜单集合
	 *
	 * @return List
	 */
	public List<Resource> getMenusList() {
		return menusList;
	}

	/**
	 * 设置当前用户拥有的菜单集合
	 *
	 * @param menusList 资源集合
	 */
	public void setMenusList(List<Resource> menusList) {
		this.menusList = menusList;
	}

	/**
	 * 包含所有类型的安全树
	 * @return
	 */
	public List<Resource> getResourceList() {
		return resourceList;
	}

	/**
	 * 包含所有类型的安全树
	 * @param resourceList
	 */
	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}

	public boolean checkRole(String role) {
		return org.springframework.util.CollectionUtils.contains(this.geRoleNames().iterator(), role);
	}

	@Override
	public boolean isSystem(){
		return this.checkRole(SYSTEM_SUPER_ADMIN_ROLE_NAME);
	}


	@Override
	public List<String> getUserParentDeptIds(boolean containSelf) {
		return extractToList(this.getUserParentDepts(containSelf), "id") ;

	}

	public List<Department> getUserParentDepts(boolean containSelf) {
        String ignoreTypes = DepartmentType.ORGANIZATION.getValue()+","+DepartmentType.GROUP.getValue();
		DepartmentService deptService = SpringContextHolder.getBean(DepartmentService.class);
        List<Department> allParents = Lists.newArrayList();

        for(Department department: departmentList){
            allParents.addAll(deptService.getAllParent(department.getId(),containSelf,ignoreTypes));
        }
		return allParents;
	}


	@Override
	public List<String> getUserGroupIds() {
		// TODO 获取用户组ID集合  未实现
		return new ArrayList<String>();
	}

	@Override
	public List<String> geRoleNames() {
		return extractToList(this.rolesList, "name");
	}

	@Override
	public List<String> getRoleIds() {
		return extractToList(this.rolesList, "id");
	}

	@Override
	public List<String> getPostIds() {
		return extractToList(this.postList, "id") ;
	}


	@Override
	public List<String> getUserChildOrgIds(boolean containSelf) {
		return extractToList(this.getUserChildOrgs(containSelf), "id") ;

	}

    @Override
	public List<Department> getUserChildOrgs(boolean containSelf) {
        String ignoreTypes = DepartmentType.DEPARTMENT.getValue()+","+DepartmentType.GROUP.getValue();
		return getChildDepartments(getUserOrgId(),containSelf,ignoreTypes);
	}


    @Override
    public List<String> getDepartmentIds() {
        return extractToList(this.departmentList,"id");
    }

    /**
     * 获取department 列表
     * @param deptId departmentId
     * @param containSelf 是否包含当前department
     * @param ignoreTypes 忽略的类型 , 多个以 , 逗号区分
     * @return list of {@link xxx.yyy.sys.security.model.Department}
     */
    private List<Department> getChildDepartments(String deptId,boolean containSelf,String ignoreTypes) {
        DepartmentService deptService = SpringContextHolder.getBean(DepartmentService.class);
        List<Department> departments = deptService.getAllChildDepts(getUserOrgId(),containSelf, ignoreTypes);
        return departments;
    }

    @Override
    public List<String> getAllChildDepartmentIds(boolean containSelf) {
        return extractToList(getAllChildDepartments(containSelf),"id");
    }

    @Override
    public List<Department> getAllChildDepartments(boolean containSelf) {
        String ignoreTypes = DepartmentType.ORGANIZATION.getValue()+","+DepartmentType.GROUP.getValue();

        List<Department> all = Lists.newArrayList();
        for(Department dept:departmentList){
            all.addAll(getChildDepartments(dept.getId(),containSelf,ignoreTypes));
        }
        return all;
    }

    /**
	 * @return the postList
	 */
	public List<Post> getPostList() {
		return postList;
	}

	/**
	 * @param postList the postList to set
	 */
	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}


    @Override
    public String getUserOrgId() {
        return user.getOrgId();
    }

    /**
     * 获取当前用户的单位
     */
	@Override
	public Department getUserOrg() {
        DepartmentService deptService = SpringContextHolder.getBean(DepartmentService.class);
		return deptService.findOne(this.getUserOrgId());
	}

	/**
	 * 获取当前用户默认部门
	 * @return
	 */
	public Department getUserDefaultDept() {
		DepartmentService deptService = SpringContextHolder.getBean(DepartmentService.class);
		return deptService.findOne(getUser().getDefaultDeptId());

	}

	@Override
	public Department getUserRootOrg() {
		DepartmentService deptService = SpringContextHolder.getBean(DepartmentService.class);
		return deptService.findRootDept(this.getUserOrgId());
	}

	@Override
	public String getUserRootOrgId() {
		 return this.getUserRootOrg().getId();
	}


    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
}
