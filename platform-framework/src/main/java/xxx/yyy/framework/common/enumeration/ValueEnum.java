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
 * 针对键为String值任意Object类型的枚举接口父类
 * 
 * @author serv
 *
 * @param <V>
 */
public interface ValueEnum<V> {
	
	/**
	 * 获取值
	 * 
	 * @return Object
	 */
	public V getValue();

	/**
	 * 获取名称
	 * 
	 * @return String
	 */
	public String getName();
}

