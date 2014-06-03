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

import xxx.yyy.sys.base.context.AbstractContext;
import xxx.yyy.sys.rbac.model.*;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 系统常用变量模型实体
 * 
 * @author izerui.com
 * 
 */
public class SessionVariable extends AbstractContext implements Serializable{

    // 当前用户
	private User user;

	// 当前用户所在的角色集合
	private List<Role> rolesList;

	// 当前用户的授权资源集合
	private List<Resource> authorizationInfo;

    //当前用户的所属部门列表 用户:部门  n:n
    private List<Department> departmentList;


	//TODO 用户数据权限规则
//	private List<SecretRule> secretRuleList ;


    //岗位列表
	private List<Post> postList;


    @Override
    public User getUser() {
        //本地机器环境，或者测试环境下使用
        if(null==user){
            return ThreadLocalUserContext.getUser();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public List<Role> getRolesList() {
        if(null==rolesList&&null!=user){
            rolesList = accountService.getUserRoles(getUser().getId());
        }
        return rolesList;
    }

    public void setRolesList(List<Role> rolesList) {
        this.rolesList = rolesList;
    }

    @Override
    public List<Resource> getAuthorizationInfo() {
        if(null==authorizationInfo&&null!=user){
            authorizationInfo = resourceService.getUserResources(getUser().getId());
        }
        return authorizationInfo;
    }

    public void setAuthorizationInfo(List<Resource> authorizationInfo) {
        this.authorizationInfo = authorizationInfo;
    }

    @Override
    public List<Department> getDepartmentList() {
        if(null==departmentList&&null!=user){
            departmentList = accountService.findOne(getUser().getId()).getDeptList();
        }
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    @Override
    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        if(null==postList&&null!=user){
            postList = postService.getUserPosts(getUser().getId());
        }
        this.postList = postList;
    }
}
