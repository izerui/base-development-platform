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
package xxx.yyy.sys.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import xxx.yyy.framework.common.enumeration.ResourceType;
import xxx.yyy.framework.common.model.BaseTreeInfoModel;
import xxx.yyy.framework.common.utilities.I18nUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源安全实体
 * 
 * @author izerui.com
 * 
 */
@Entity
@Table(name = "SYS_RBAC_RESOURCE")
public class Resource extends BaseTreeInfoModel{

    /**
     * 父分类
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
    private Resource parent ;


    @OrderBy("sort ASC")
    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY,cascade={CascadeType.ALL})
    private List<Resource> children;


	// action url
    @Column(name = "VALUE", length = 256)
	private String value;

	// 资源类型

	// 资源所对应的组集合
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_RBAC_ROLE_RESOURCE", joinColumns = { @JoinColumn(name = "SYS_RBAC_RESOURCE_ID") }, inverseJoinColumns = { @JoinColumn(name = "SYS_RBAC_ROLE_ID") })
	private List<Role> roles = new ArrayList<Role>();

	// shiro permission 字符串
    @Column(name = "PERMISSION", length = 64)
	private String permission;

	// 图标
    @Column(name = "ICON", length = 32)
	private String icon;

	// 响应的url
	private String url;

    /**
     * 构造方法
     * @param type
     */
    public Resource(String type) {
        super(type);
    }

    /**
	 * 获取资源权限url
	 * 
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置资源操作URL
	 * 
	 * @param value
	 *            资源操作URL
	 */
	public void setValue(String value) {
		this.value = value;
	}



	/**
	 * 获取该资源对应的角色集合
	 * 
	 * @return List
	 */
	@JsonIgnore
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * 设置该资源对应的角色集合
	 * 
	 * @param roles
	 *            角色集合
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * 获取permission字符串
	 * 
	 * @return String
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * 设置permission字符串
	 * 
	 * @param permission
	 *            字符串
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}


	/**
	 * 获取菜单URL
	 * 
	 * @return url
	 */
	@Column(name = "URL", length = 254)
	public String getUrl() {
		return url;
	}

	/**
	 * 设置菜单url
	 * 
	 * @param url
	 *            菜单url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取资源类型的名称
	 * 
	 * @return String
	 */
	@Transient
	public String getTypeName() {
		return I18nUtils.getMessage(ResourceType.class, type);
	}


	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

    @JsonIgnore
    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    @JsonIgnore
    public List<Resource> getChildren() {
        return children;
    }

    public void setChildren(List<Resource> children) {
        this.children = children;
    }

}
