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
import com.google.common.collect.Maps;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.framework.common.enumeration.DepartmentType;
import xxx.yyy.framework.common.utilities.CollectionUtils;
import xxx.yyy.sys.datafilter.model.FilterRule;
import xxx.yyy.sys.datafilter.service.FilterRuleService;
import xxx.yyy.sys.rbac.model.*;
import xxx.yyy.sys.rbac.service.AccountService;
import xxx.yyy.sys.rbac.service.DepartmentService;
import xxx.yyy.sys.rbac.service.PostService;
import xxx.yyy.sys.rbac.service.ResourceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.collections.CollectionUtils.transformedCollection;
import static xxx.yyy.framework.common.utilities.CollectionUtils.extractToList;

/**
 * 
 * 用户上下文模型实体
 * 
 * @author izerui.com
 * 
 */
public class SessionVariable implements DepartmentContext,FilterRuleContext,PostContext,ResourceContext,RoleContext,UserContext{


    // 当前用户
    private User user;


    public SessionVariable(User user) {
        this.user = user;
    }


    /**
     * 获取department 列表
     * @param deptId departmentId
     * @param containSelf 是否包含当前department
     * @param ignoreTypes 忽略的类型 , 多个以 , 逗号区分
     * @return list of {@link xxx.yyy.sys.rbac.model.Department}
     */
    private List<Department> getChildDepartments(String deptId,boolean containSelf,String ignoreTypes) {
        String key = "getChildDepartments"+containSelf;
        if(getCache(key)!=null){
            return getCache(key);
        }
        return putCache(key,SpringContextHolder.getBean(DepartmentService.class).findOne(getUserOrgId()).mergerTolist(containSelf,ignoreTypes));
    }


    @Override
    public User getUser() {
        return user;
    }


    @Override
    public String getUserOrgId() {
        return getUser().getOrgId();
    }

    @Override
    public Department getUserOrg() {
        String key = "getUserOrg";
        if(getCache(key)!=null){
            return getCache(key);
        }
        return putCache(key,SpringContextHolder.getBean(DepartmentService.class).findOne(this.getUserOrgId()));
    }

    @Override
    public Department getUserRootOrg() {
        String key = "getUserRootOrg";
        if(getCache(key)!=null){
            return getCache(key);
        }
        return putCache(key,SpringContextHolder.getBean(DepartmentService.class).findRootDept(this.getUserOrgId()));
    }

    @Override
    public String getUserRootOrgId() {
        return this.getUserRootOrg().getId();
    }

    @Override
    public Department getUserDefaultDept() {
        String key = "getUserDefaultDept";
        if(getCache(key)!=null){
            return getCache(key);
        }
        return putCache(key,SpringContextHolder.getBean(DepartmentService.class).findOne(getUser().getDefaultDeptId()));
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

        String key = "getUserParentDepts"+containSelf;
        if(getCache(key)!=null){
            return getCache(key);
        }

        List<Department> allParents = Lists.newArrayList();

        for(Department department: getDepartmentList()){
            allParents.addAll(SpringContextHolder.getBean(DepartmentService.class).getAllParent(department.getId(),containSelf,IGNORE_ORG_GROUP));
        }
        return putCache(key,allParents);
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

    @Override
    public List<Role> getRolesList() {
        String key = "getRolesList";
        if(getCache(key)!=null){
            return getCache(key);
        }
        return putCache(key,SpringContextHolder.getBean(AccountService.class).findOne(getUser().getId())).getRoles();
    }

    @Override
    public List<Resource> getAuthorizationInfo() {
        String key = "getAuthorizationInfo";
        if(getCache(key)!=null){
            return getCache(key);
        }
        return putCache(key,SpringContextHolder.getBean(ResourceService.class).getUserResources(getUser().getId()));
    }

    @Override
    public List<Department> getDepartmentList() {
        String key = "getDepartmentList";
        if(getCache(key)!=null){
            return getCache(key);
        }
        return putCache(key,getIgnoreTypeList(DepartmentType.GROUP.getValue()));
    }


    @Override
    public List<Post> getPostList() {
        String key = "getPostList";
        if(getCache(key)!=null){
            return getCache(key);
        }
        return putCache(key,SpringContextHolder.getBean(PostService.class).getUserPosts(getUser().getId()));
    }

    public List<Department> getGroupList() {
        String key = "getGroupList";
        if(getCache(key)!=null){
            return getCache(key);
        }
        return putCache(key,getIgnoreTypeList(DepartmentType.GROUP.getValue()));
    }

    /**
     * 获取当前用户忽略类型的集合
     * @param ignoreTypes
     * @return
     */
    private List<Department> getIgnoreTypeList(final String ignoreTypes){

        //当前用户的关联的 部门和群组的list
        List<Department> deptAndGroupList ;

        String key = "getIgnoreTypeList";
        if(getCache(key)!=null){
            deptAndGroupList = getCache(key);
        }else{
            deptAndGroupList = SpringContextHolder.getBean(AccountService.class).findOne(getUser().getId()).getDeptList();
        }

        return (List<Department>) CollectionUtils.collect(deptAndGroupList, new Transformer() {
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






    public List<FilterRule> getFilterRuleList() {

        String key = "getFilterRuleList";
        if(getCache(key)!=null){
            return getCache(key);
        }
        List<String> relationIds = Lists.newArrayList(getUser().getId());//添加当前用户id
        relationIds.addAll(getRoleIds());//添加所属角色ids
        relationIds.addAll(getDepartmentIds());//添加部门ids
        relationIds.addAll(getPostIds());//添加岗位ids
        return putCache(key,SpringContextHolder.getBean(FilterRuleService.class).queryUnDeleted().findAll("x.relationId in ?1",relationIds));
    }

    @Override
    public List<String> getFilterRuleJpqlList(final Class modelClass, final String dataFilterType) {
        return (List<String>) transformedCollection(getFilterRuleList(), new Transformer() {
            @Override
            public Object transform(Object input) {
                FilterRule fr = (FilterRule) input;
                if (StringUtils.equals(modelClass.getName(), fr.getModelClassName())) {
                    if (StringUtils.equals(dataFilterType, fr.getOperationType())) {
                        return fr.getConditionJpql();
                    }
                }
                return null;
            }
        });
    }


    public Map<String,Object> getFilterParameters(){
        Map<String,Object> filterParameters = Maps.newHashMap();
        filterParameters.put("userId", getUser().getId());
        filterParameters.put("userOrgId", getUserOrgId());
        filterParameters.put("userParantDeptIds", getUserParentDeptIds(true));
        filterParameters.put("userDefaultDept", getUserDefaultDeptId());
        filterParameters.put("userAllChildDeptIds", getAllChildDepartmentIds(true));
        filterParameters.put("userChildOrgIds", getUserChildOrgIds(true));
        filterParameters.put("userSecretLevel",getUser().getSecretLevel());
        filterParameters.put("dataNow",new DateTime().toDate());
        return filterParameters;
    }

    private Map<String, Object> cache = new HashMap<String, Object>();

    private <T> T getCache(String key){
        return (T) cache.get(key);
    }
    private <T> T putCache(String key,T object){
        this.cache.put(key, object);
        return object;
    }


}
