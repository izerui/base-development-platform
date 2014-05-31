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
 * 业务数据状态
 * 
 * @author serv
 *
 */
public  enum BusinessStatus implements ValueEnum<Integer>{
	
	
	/**
	 * 废弃
	 */
	DISCARD ("sys.base.business.status.discard",0),
	
	/**
	 * 草稿
	 */
	DRAFT   ("sys.base.business.status.draft",1),

	/**
	 * 审核
	 */
	AUDIT ("sys.base.business.status.audit",2),
	
	/**
	 * 发布
	 */
	PUBLISH ("sys.base.business.status.publish",100)

	;
 
	private String name ;
	
	private  int value ;
 
	
	private BusinessStatus(String name , int value) {
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
