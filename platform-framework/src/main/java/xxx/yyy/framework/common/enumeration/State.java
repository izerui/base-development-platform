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
 * 状态枚举
 * 
 * @author serv
 *
 */
public enum State implements ValueEnum<Integer>{
	
	/**
	 * 启用
	 */
	Enable(1,"sys.base.state.enable"),
	/**
	 * 禁用
	 */
	Disable(2,"sys.base.state.disable");
	 
	
	//值
	private Integer value;
	//名称
	private String name;
	
	private State(Integer value,String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * 获取值
	 * @return Integer
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * 获取名称
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
}
