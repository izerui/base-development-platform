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
 * 权限类型枚举
 * @author serv
 *
 */
public enum DataFilterType {

	/**
	 * 创建权限
	 */
	CREATE("CREATE")

	/**
	 * 阅读权限
	 */
	, READ("READ")


	/**
	 * 打印权限
	 */
	,PRINGT("PRINGT")


	/**
	 * 删除权限
	 */
	, DELETE("DELETE")


	/**
	 * 编辑权限
	 */
	, UPDATE("UPDATE")

	;

	private String value;

	private DataFilterType(String value) {
		this.value = value;
	}

    public String getValue() {
        return value;
    }
}
