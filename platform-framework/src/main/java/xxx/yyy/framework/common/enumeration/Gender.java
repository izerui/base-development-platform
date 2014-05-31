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
 * 性别枚举
 * @author serv
 *
 */
public enum Gender implements ValueEnum<Integer>{
	
	
	/**
	 * 女
	 */
	FEMALE ("sys.base.gender.female",0),
	
	/**
	 * 男
	 */
	MALE   ("sys.base.gender.male",1),

	

	;
 
	private String name ;
	
	private  int value ;
 
	
	private Gender(String name , int value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public Integer getValue() {
		
		return value;
	}

	@Override
	public String getName() {
		
		return name;
	}
	
	 
	
	
	
}
