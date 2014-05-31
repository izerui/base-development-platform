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
package xxx.yyy.framework.common.web;

import java.io.Serializable;

/**
 * 
 * 系统常用变量模型实体
 * 
 * @author izerui.com
 * 
 */
public class SessionVariable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
//
//	// 当前用户
//	private User user;
//
//	// 当前用户所在的角色集合
//	private List<Role> rolesList;
//
//	// 当前用户的授权资源集合
//	private List<Resource> authorizationInfo;
//
//	// 菜单树
//	private List<Resource> menusList;
//
//	//包含所有类型的安全树
//	private List<Resource> resourceList;
//
//	//主题
//	private Map<String,String> themes;
//
//
//	//用户数据权限规则
//	private List<SecretRule> secretRuleList ;
//
//
//	private List<Post> postList  ;
//
//
//	public SessionVariable() {
//
//	}
//
//	public SessionVariable(User user) {
//		this.user = user;
//	}
//
//	public SessionVariable(User user, List<Role> rolesList, List<Resource> authorizationInfo, List<Resource> menusList) {
//		this.user = user;
//		this.rolesList = rolesList;
//		this.authorizationInfo = authorizationInfo;
//		this.menusList = menusList;
//	}
//
//	/**
//	 * 获取当前用户
//	 *
//	 * @return {@link User}
//	 */
//	public User getUser() {
//		return user;
//	}
//
//	/**
//	 * 设置当前用户
//	 *
//	 * @param user 当前用户
//	 */
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	/**
//	 * 获取当前用户所在的组集合
//	 *
//	 * @return List
//	 */
//	public List<Role> getRolesList() {
//		return rolesList;
//	}
//
//	/**
//	 * 设置当前用户所在的组集合
//	 *
//	 * @param rolesList 组集合
//	 */
//	public void setRolesList(List<Role> rolesList) {
//		this.rolesList = rolesList;
//	}
//
//	/**
//	 * 获取当前用户的所有授权资源集合
//	 *
//	 * @return List
//	 */
//	public List<Resource> getAuthorizationInfo() {
//		return authorizationInfo;
//	}
//
//	/**
//	 * 设置当前用户的所有授权资源集合
//	 *
//	 * @param authorizationInfo 资源集合
//	 */
//	public void setAuthorizationInfo(List<Resource> authorizationInfo) {
//		this.authorizationInfo = authorizationInfo;
//	}
//
//	/**
//	 * 获取当前用户拥有的菜单集合
//	 *
//	 * @return List
//	 */
//	public List<Resource> getMenusList() {
//		return menusList;
//	}
//
//	/**
//	 * 设置当前用户拥有的菜单集合
//	 *
//	 * @param menusList 资源集合
//	 */
//	public void setMenusList(List<Resource> menusList) {
//		this.menusList = menusList;
//	}
//
//	public Map<String, String> getThemes() {
//		return themes;
//	}
//
//	public void setThemes(Map<String, String> themes) {
//		this.themes = themes;
//	}
//	/**
//	 * 包含所有类型的安全树
//	 * @return
//	 */
//	public List<Resource> getResourceList() {
//		return resourceList;
//	}
//
//	/**
//	 * 包含所有类型的安全树
//	 * @param resourceList
//	 */
//	public void setResourceList(List<Resource> resourceList) {
//		this.resourceList = resourceList;
//	}
//
//	@Override
//	public boolean checkRole(final String role) {
//		return org.springframework.util.CollectionUtils.contains(this.getUserRoleNames().iterator(), role);
//		//return CollectionUtils.containsAny(this.getCurrentUserRoleNames(),role);
//	}
//
//	@Override
//	public boolean isSystemSuperAdmin(){
//		return this.checkRole(SystemSecurityConstants.SYSTEM_SUPER_ADMIN_ROLE_NAME);
//	}
//
//
//	@Override
//	public String getUserOrgId() {
// 		return user.getOrgId();
//	}
//
//	@Override
//	public String getUserId() {
//		return this.user.getId();
//	}
//
//	@Override
//	public Integer getUserSecretLevel() {
//		return this.user.getSecretLevel();
//	}
//
//	@Override
//	public List<String> getUserChildDeptIds(boolean current) {
//
//		return CollectionUtils.extractToList(this.getUserChildDepts(current),"id") ;
//	}
//
//
//	public List<Dept> getUserChildDepts(boolean current) {
//		String key = "getUserChildDepts"+current;
//		if(getCache(key)!=null){
//			return (List<Dept>) getCache(key);
//		}
//
//		DeptService deptService = SpringContextHolder.getBean(DeptService.class);
//		List<Dept> depts = deptService.getAllChildDepts(this.user.getDefaultDeptId());
//		if(current){
//			depts.add(deptService.findById(this.user.getDefaultDeptId()));
//		}
//		return putCache(key,depts);
//	}
//
//	@Override
//	public List<String> getUserParentDeptIds(boolean current) {
//		return CollectionUtils.extractToList(this.getUserParentDepts(current),"id") ;
//
//	}
//
//	public List<Dept> getUserParentDepts(boolean current) {
//
//		String key = "getUserParentDepts"+current;
//		if(getCache(key)!=null){
//			return (List<Dept>) getCache(key);
//		}
//
//
//		DeptService deptService = SpringContextHolder.getBean(DeptService.class);
//		List<Dept> depts = deptService.findAllParentDepts(this.user.getDefaultDeptId());
//		if(current){
//			depts.add(deptService.findById(this.user.getDefaultDeptId()));
//		}
//		return putCache(key,depts);
//	}
//
//
//	@Override
//	public List<String> getUserGroupIds() {
//		// TODO 获取用户组ID集合  未实现
//		return new ArrayList<String>();
//	}
//
//	@Override
//	public List<String> getUserRoleNames() {
//		return CollectionUtils.extractToList(this.rolesList, "name");
//	}
//
//	@Override
//	public List<String> getUserRoleIds() {
//		return CollectionUtils.extractToList(this.rolesList, "id");
//	}
//
//	public List<Role> getCurrentUserRoles() {
//		return this.rolesList ;
//	}
//
//	@Override
//	public List<String> getUserPostIds() {
//
//		return CollectionUtils.extractToList(this.getCurrentUserPosts(),"id") ;
//
//	}
//
//
//	public List<Post> getCurrentUserPosts() {
//		return this.postList;
//	}
//
//	@Override
//	public List<String> getUserChildOrgIds(boolean current) {
//		return CollectionUtils.extractToList(this.getCurrentUserChildOrgs(current),"id") ;
//
//	}
//
//	public List<Dept> getCurrentUserChildOrgs(boolean current) {
//
//
//		String key = "getCurrentUserChildOrgs"+current;
//		if(getCache(key)!=null){
//			return (List<Dept>) getCache(key);
//		}
//
//		DeptService deptService = SpringContextHolder.getBean(DeptService.class);
//		List<Dept> orgs = deptService.getAllChildOrgs(this.user.getOrgId());
//		if(current){
//			orgs.add(deptService.findById(this.user.getOrgId()));
//		}
//		return putCache(key,orgs);
//	}
//
//
//
//
//
//	/**
//	 * @return the secretRuleList
//	 */
//	@Override
//	public List<SecretRule> getSecretRuleList() {
//		return secretRuleList;
//	}
//
//	/**
//	 * @param secretRuleList the secretRuleList to set
//	 */
//	public void setSecretRuleList(List<SecretRule> secretRuleList) {
//		this.secretRuleList = secretRuleList;
//	}
//
//	@Override
//	public List<SecretRule> getSecretRuleList(String secretType,String docType) {
//
//		List<SecretRule> list = new ArrayList<SecretRule>();
//
//		if(this.secretRuleList==null){
//			return list;
//		}
//
//		for(SecretRule rule : secretRuleList){
//			if(secretType.equals(rule.getSecretType()) && docType.equals(rule.getDocType()) ){
//				list.add(rule);
//			}
//		}
//		return list ;
//
//	}
//
//
//
//	/**
//	 * @return the postList
//	 */
//	public List<Post> getPostList() {
//		return postList;
//	}
//
//	/**
//	 * @param postList the postList to set
//	 */
//	public void setPostList(List<Post> postList) {
//		this.postList = postList;
//	}
//
//	@Override
//	public Dept getUserOrg() {
//		String key = "getUserOrg";
//		if(getCache(key)!=null){
//			return (Dept) getCache(key);
//		}
//		DeptService deptService = SpringContextHolder.getBean(DeptService.class);
//		return putCache(key,deptService.findById(this.getUserOrgId()));
//
//
//	}
//
//	/**
//	 * 获取当前用户默认部门
//	 * @return
//	 */
//	public Dept getUserDefaultDept() {
//		String key = "getUserDefaultDept";
//		if(getCache(key)!=null){
//			return (Dept) getCache(key);
//		}
//		DeptService deptService = SpringContextHolder.getBean(DeptService.class);
//		return putCache(key,deptService.findById(this.getUser().getDefaultDeptId()));
//
//
//	}
//
//	@Override
//	public Dept getUserRootOrg() {
//		 String key = "getUserRootOrg";
//		 if(getCache(key)!=null){
//			 return (Dept) getCache(key);
//		 }
//
//
//		DeptService deptService = SpringContextHolder.getBean(DeptService.class);
//		return putCache(key,deptService.findRootDept(this.getUserOrgId()));
//	}
//
//	@Override
//	public String getUserRootOrgId() {
//		 return this.getUserRootOrg().getId();
//	}
//
//
//	private Map<String, Object> cache = new HashMap<String, Object>();
//
//	private Object getCache(String key){
//		return cache.get(key);
//	}
//	private <T> T putCache(String key,T object){
//		this.cache.put(key, object);
//		return object;
//	}
//
	 

}
