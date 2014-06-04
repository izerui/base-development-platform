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
package xxx.yyy.sys.security.realm;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.Assert;
import xxx.yyy.framework.common.utilities.CollectionUtils;
import xxx.yyy.sys.base.context.SessionVariable;
import xxx.yyy.sys.rbac.model.Resource;
import xxx.yyy.sys.rbac.model.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* apache shiro 的公用授权类
*
* @author izerui.com
*
*/
public abstract class AuthorizationRealm extends AuthorizingRealm {


	private List<String> defaultPermission = Lists.newArrayList();

	private List<String> defaultRole = Lists.newArrayList();






	/**
	 * 设置默认permission
	 *
	 * @param defaultPermissionString
	 *            permission 如果存在多个值，使用逗号","分割
	 */
	public void setDefaultPermissionString(String defaultPermissionString) {
		String[] perms = StringUtils.split(defaultPermissionString, ",");
		CollectionUtils.addAll(defaultPermission, perms);
	}

	/**
	 * 设置默认role
	 *
	 * @param defaultRoleString
	 *            role 如果存在多个值，使用逗号","分割
	 */
	public void setDefaultRoleString(String defaultRoleString) {
		String[] roles = StringUtils.split(defaultRoleString, ",");
		CollectionUtils.addAll(defaultRole, roles);
	}

	/**
	 * 设置默认permission
	 *
	 * @param defaultPermission
	 *            permission
	 */
	public void setDefaultPermission(List<String> defaultPermission) {
		this.defaultPermission = defaultPermission;
	}

	/**
	 * 设置默认role
	 *
	 * @param defaultRole
	 *            role
	 */
	public void setDefaultRole(List<String> defaultRole) {
		this.defaultRole = defaultRole;
	}

	/**
	 *
	 * 当用户进行访问链接时的授权方法
	 *
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		SessionVariable model = principals.oneByType(SessionVariable.class);

		Assert.notNull(model, "找不到principals中的SessionVariable");

		// 添加用户拥有的permission
		addPermissions(info, model.getAuthorizationInfo());
		// 添加用户拥有的role
		addRoles(info, model.getRolesList());

		return info;
	}

	/**
	 * 通过组集合，将集合中的role字段内容解析后添加到SimpleAuthorizationInfo授权信息中
	 *
	 * @param info
	 *            SimpleAuthorizationInfo
	 * @param groupsList
	 *            组集合
	 */
	private void addRoles(SimpleAuthorizationInfo info, List<Role> groupsList) {


		/*
		// 解析当前用户组中的role
		List<String> temp = CollectionUtils.extractToList(groupsList, "role",
				true);

		List<String> roles = getValue(temp, "roles\\[(.*?)\\]");
	*/


		List<String> roles =CollectionUtils.extractToList(groupsList, "name",true);

		// 添加默认的roles到roels
		if (CollectionUtils.isNotEmpty(defaultRole)) {
			CollectionUtils.addAll(roles, defaultRole.iterator());
		}

		// 将当前用户拥有的roles设置到SimpleAuthorizationInfo中
		info.addRoles(roles);

	}

	/**
	 * 通过资源集合，将集合中的permission字段内容解析后添加到SimpleAuthorizationInfo授权信息中
	 *
	 * @param info
	 *            SimpleAuthorizationInfo
	 * @param authorizationInfo
	 *            资源集合
	 */
	private void addPermissions(SimpleAuthorizationInfo info,
			List<Resource> authorizationInfo) {
		// 解析当前用户资源中的permissions
		List<String> temp = CollectionUtils.extractToList(authorizationInfo,
				"permission", true);
		List<String> permissions = getValue(temp, "perms\\[(.*?)\\]");

		// 添加默认的permissions到permissions
		if (CollectionUtils.isNotEmpty(defaultPermission)) {
			CollectionUtils.addAll(permissions, defaultPermission.iterator());
		}

		// 将当前用户拥有的permissions设置到SimpleAuthorizationInfo中
		info.addStringPermissions(permissions);

	}

	/**
	 * 通过正则表达式获取字符串集合的值
	 *
	 * @param obj
	 *            字符串集合
	 * @param regex
	 *            表达式
	 *
	 * @return List
	 */
	private List<String> getValue(List<String> obj, String regex) {

		List<String> result = new ArrayList<String>();

		if (CollectionUtils.isEmpty(obj)) {
			return result;
		}

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(StringUtils.join(obj, ","));

		while (matcher.find()) {
			result.add(matcher.group(1));
		}

		return result;
	}
}
