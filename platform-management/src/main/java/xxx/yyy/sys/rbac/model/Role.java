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
import xxx.yyy.sys.base.model.BaseInfoModel;
import xxx.yyy.framework.common.utilities.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色实体
 * 
 * @author serv
 *
 */
@Entity
@Table(name="SYS_RBAC_ROLE")
public class Role extends BaseInfoModel {
	
	//成员
    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(name = "SYS_RBAC_ROLE_USER", joinColumns = { @JoinColumn(name = "SYS_RBAC_ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "SYS_RBAC_USER_ID") })
	private List<User> users = new ArrayList<User>();


	//拥有资源
    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(name = "SYS_RBAC_ROLE_RESOURCE", joinColumns = { @JoinColumn(name = "SYS_RBAC_ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "SYS_RBAC_RESOURCE_ID") })
	private List<Resource> resources = new ArrayList<Resource>();

	//shiro role 字符串
    @Column(name="ROLE",length=64)
	private String role;

	//shiro role连定义的值
    @Column(name="VALUE",length=256)
	private String value;
	
 

	/**
	 * 获取角色成员
	 * 
	 * @return List
	 */
	@JsonIgnore
	public List<User> getUsers() {
		return users;
	}

	/**
	 * 设置角色成员
	 * 
	 * @param users 用户集合
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * 获取拥有资源
	 * 
	 * @return List
	 */
	@JsonIgnore
	public List<Resource> getResources() {
		return resources;
	}

	/**
	 * 设置该角色的操作资源
	 * 
	 * @param resources 资源集合
	 */
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	


	/**
	 * 获取所有成员的id
	 * 
	 * @return List
	 */
	@Transient
	public List<String> geUserIds() {
		return CollectionUtils.extractToList(this.users, "id");
	}

	
	/**
	 * 获取shiro role字符串
	 * @return String
	 */
	public String getRole() {
		return role;
	}

	/**
	 * 设置shiro role字符串
	 * @param role 字符串
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * 获取shiro role连定义的值
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置 shiro role连定义的值
	 * @param value 值
	 */
	public void setValue(String value) {
		this.value = value;
	}





	@Override
	public boolean equals(Object obj) {
		if(this.name!=null&&((Role)obj).getName()!=null&&this.name.equals(((Role)obj).getName())){
			return true;
		}
		return false;
	}


	
	
}
