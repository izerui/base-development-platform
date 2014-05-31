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
 * 组类型
 * 
 * @author serv
 *
 */
public enum GroupType implements ValueEnum<String>{

	/**
	 * 机构类型
	 */
	Organization("01","group.type.organization"),
	/**
	 * 部门类型 
	 */
	Department("02","group.type.department"),
	/**
	 * 职能岗位
	 */
	RoleGorup("03","group.type.job");
	
	private GroupType(String value,String name) {
		this.value = value;
		this.name = name;
	}
	
	private String value;
	
	private String name;

	/**
	 * 获取类型值
	 * 
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 获取类型名称
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
}
