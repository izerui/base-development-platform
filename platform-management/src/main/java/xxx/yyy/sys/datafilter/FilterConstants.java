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
package xxx.yyy.sys.datafilter;


/**
 * 数据权限相关常量定义
 * @author serv
 *
 */
public interface FilterConstants {
	
	/**
	 * 任何人权限  类似 获取的时候如下：  where secretId in (userId,deptId,roleId,_ANYONE)
	 */
	public  static  final String RELATION_ID_ANYONE = "_ANYONE" ;
	
	
}
