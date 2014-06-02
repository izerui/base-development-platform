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
package xxx.yyy.sys.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import xxx.yyy.sys.security.model.Resource;
import xxx.yyy.sys.security.model.Role;
import xxx.yyy.sys.security.service.AccountService;
import xxx.yyy.sys.security.service.ResourceService;

/**
 * 借助spring {@link org.springframework.beans.factory.FactoryBean} 对apache shiro的premission进行动态创建
 *
 * @author izerui.com
 *
 */
public class ChainDefinitionSectionMetaSource implements
        FactoryBean<Section> {

	@Autowired
	private AccountService accountService;
	@Autowired
	private ResourceService resourceService;

	// shiro默认的链接定义
	private String filterChainDefinitions;

	/**
	 * 通过filterChainDefinitions对默认的链接过滤定义
	 * 
	 * @param filterChainDefinitions
	 *            默认的接过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	@Override
	public Section getObject() throws BeansException {
		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		// 循环数据库资源的url
		for (Resource resource : resourceService.findAll()) {
			if (StringUtils.isNotEmpty(resource.getValue())
					&& StringUtils.isNotEmpty(resource.getPermission())) {
				section.put(resource.getValue(), resource.getPermission());
			}
		}

		// 循环数据库角色的url
		for (Role group : accountService.getRoles()) {
			if (StringUtils.isNotEmpty(group.getValue())
					&& StringUtils.isNotEmpty(group.getRole())) {
				section.put(group.getValue(), group.getRole());
			}
		}

		return section;
	}

	@Override
	public Class<?> getObjectType() {
		return Section.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
