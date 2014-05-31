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
package xxx.yyy.framework.common.enumeration;

/**
 * 资源类型枚举
 * 
 * @author serv
 *
 */
public enum ResourceType implements ValueEnum<String>{
	
	/**
	 * 菜单类型，该类型为用户可以见的
	 */
	Menu("01","sys.resource.type.menu"),
	/**
	 * 安全类型，该类型为shiro拦截的并且用户不可见的
	 */
	Security("02","sys.resource.type.security");
	
	ResourceType(String value,String name) {
		this.name = name;
		this.value = value;
	}
	
	private String name;
	
	private String value;
	
	/**
	 * 获取资源类型名称
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取资源类型值
	 * @return
	 */
	public String getValue() {
		return value;
	}
	
	
}
