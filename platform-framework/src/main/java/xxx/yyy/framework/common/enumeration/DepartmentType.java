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
 * 
 * 组织架构类型
 * @author tengda
 *
 */
public enum DepartmentType implements ValueEnum<String>{


	/**
	 * 部门
	 */
	DEPARTMENT("sys.rbac.department","0"),

	/**
	 * 机构
	 */
    ORGANIZATION ("sys.rbac.organization","1"),

	/**
	 * 群组
	 */
	GROUP ("sys.rbac.group","2")

	;

	private String name ;

	private  String value ;

	private DepartmentType(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getValue() {
		
		return value;
	}

	@Override
	public String getName() {
	
		return name;
	}
	
	
	
}
