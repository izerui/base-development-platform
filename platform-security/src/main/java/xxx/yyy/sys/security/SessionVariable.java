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
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import xxx.yyy.framework.common.enumeration.DepartmentType;
import xxx.yyy.framework.common.utilities.CollectionUtils;
import xxx.yyy.sys.base.context.AbstractContext;
import xxx.yyy.sys.datafilter.model.FilterRule;
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


	// 当前用户所在的角色集合
	private List<Role> rolesList;

	// 当前用户的授权资源集合
	private List<Resource> authorizationInfo;

    //当前用户的所属部门列表 用户:部门  n:n
    private List<Department> departmentList;

    //当前用户的所属群组
    private List<Department> groupList;


    //权限列表
	private List<FilterRule> filterRuleList ;


    //岗位列表
	private List<Post> postList;


    public SessionVariable(User user) {
        super(user);
    }

    @Override
    public List<Role> getRolesList() {
        if(null==rolesList&&null!=getUser()){
            rolesList = accountService.getUserRoles(getUser().getId());
        }
        return rolesList;
    }

    @Override
    public List<Resource> getAuthorizationInfo() {
        if(null==authorizationInfo&&null!=getUser()){
            authorizationInfo = resourceService.getUserResources(getUser().getId());
        }
        return authorizationInfo;
    }

    @Override
    public List<Department> getDepartmentList() {
        if(null==departmentList&&null!=getUser()){
            departmentList = getIgnoreTypeList(DepartmentType.GROUP.getValue());
        }
        return departmentList;
    }

    /**
     * 获取当前用户忽略类型的集合
     * @param ignoreTypes
     * @return
     */
    private List<Department> getIgnoreTypeList(final String ignoreTypes){
        List<Department> departmentAndGroupList = accountService.findOne(getUser().getId()).getDeptList();
        return (List<Department>) CollectionUtils.collect(departmentAndGroupList, new Transformer() {
            @Override
            public Object transform(Object o) {
                Department t = (Department) o;
                if (ignoreTypes == null || !ArrayUtils.contains(StringUtils.split(","), t.getType())) {
                    return t;
                }
                return null;
            }
        });
    }


    @Override
    public List<Post> getPostList() {
        if(null==postList&&null!=getUser()){
            postList = postService.getUserPosts(getUser().getId());
        }
        return postList;
    }

    public List<FilterRule> getFilterRuleList() {
        if(null==filterRuleList&&null!=getUser()){
            List<String> relationIds = Lists.newArrayList(getUser().getId());//添加当前用户id
            relationIds.addAll(getRoleIds());//添加所属角色ids
            relationIds.addAll(getDepartmentIds());//添加部门ids
            relationIds.addAll(getPostIds());//添加岗位ids
            filterRuleList = filterRuleService.queryUnDeleted().findAll("x.relationId in ?1",relationIds);
        }
        return filterRuleList;
    }

    public List<Department> getGroupList() {
        if(null==groupList&&null!=getUser()){
            groupList = getIgnoreTypeList(DepartmentType.GROUP.getValue());
        }
        return groupList;
    }


}
