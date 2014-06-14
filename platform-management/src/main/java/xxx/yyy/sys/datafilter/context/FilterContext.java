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
package xxx.yyy.sys.datafilter.context;

import java.util.Collection;
import java.util.Map;

/**
 * Created by serv on 14-6-5.
 */
public interface FilterContext {

    /**
     * 登录用户id
     */
    String USER_ID = "userId";
    /**
     * 用户的单位ID
     */
    String USER_ORG_ID = "userOrgId";
    /**
     * 当前单位和上级单位的id集合
     */
    String USER_PARENT_DEPT_IDS = "userParentDeptIds";
    /**
     * 用户所属部门的id集合
     */
    String USER_DEPT_IDS = "userDeptIds";
    /**
     * 用户所属群组id集合
     */
    String USER_GROUP_IDS = "userGroupIds";
    /**
     * 用户默认部门id
     */
    String USER_DEFAULT_DEPT_ID = "userDefaultDeptId";
    /**
     * 用户所属部门和子部门的id集合
     */
    String USER_CHILD_DEPT_IDS = "userChildDeptIds";
    /**
     * 用户所属单位和子单位的id集合
     */
    String USER_CHILD_ORG_IDS = "userChildOrgIds";
    /**
     * 用户的密级级别 数值越高密级越大
     */
    String USER_SECRET_LEVEL = "userSecretLevel";
    /**
     * 当前时间
     */
    String DATE_NOW = "dateNow";
    /**
     * 当前年
     */
    String DATE_YEAR = "dateYear";

    /**
     * 获取用户具有的规则权限的jpql 列表
     *
     * @param modelClass     操作的entityClass
     * @param dataFilterType 数据过滤类型 {@link xxx.yyy.sys.datafilter.DataFilterType}
     * @param orgId 单位id
     * @return jpql 列表
     */
    public Collection<String> getFilterRuleJpqlList(final Class modelClass, final String dataFilterType,final String orgId);

    /**
     * 获取用户的变量map信息
     *
     * @return
     */
    public Map<String,Object> getFilterParameters();

}
