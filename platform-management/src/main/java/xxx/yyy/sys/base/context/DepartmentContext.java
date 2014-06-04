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
package xxx.yyy.sys.base.context;

import xxx.yyy.sys.rbac.model.Department;

import java.util.List;

/**
 * 单位/部门/群组 上下文
 * Created by serv on 2014/6/2.
 */
public interface DepartmentContext {

    /**
     * 获取当前登录人员所在单位
     * @return
     */
    String getUserOrgId();


    /**
     * 获取当前登录人员所在单位
     * @return
     */
    Department getUserOrg();



    /**
     * 获取当前登录人员所在最上级单位
     * @return
     */
    Department getUserRootOrg();

    /**
     * 获取当前登录人员所在顶级单位id
     * @return
     */
    String getUserRootOrgId();

    /**
     * 获取用户的默认部门
     * @return
     */
    Department getUserDefaultDept();

    /**
     * 获取用户的默认部门ID
     * @return
     */
    String getUserDefaultDeptId();


    /**
     * 获取当前用户的子机构集合
     * @param containSelf 是否包含当前机构(单位)
     * @return
     */
    List<Department> getUserChildOrgs(boolean containSelf);


    /**
     * 获取登录用户当前部门id集合
     * @return
     */
    List<String> getDepartmentIds();

    /**
     * 获取登录用户当前部门集合
     * @return
     */
    List<Department> getDepartmentList();


    /**
     * 获取登录用户当前部门子部门id集合
     * @param containSelf 是否包含当前部门
     * @return
     */
    List<String> getAllChildDepartmentIds(boolean containSelf);


    /**
     * 获取登录用户当前部门子部门集合
     * @param containSelf 是否包含当前部门
     * @return
     */
    List<Department> getAllChildDepartments(boolean containSelf);


    /**
     * 获取登录用户当前机构子机构id集合
     * @param containSelf 是否包含当前机构
     * @return
     */
    List<String> getUserChildOrgIds(boolean containSelf);

    /**
     * 获取登录用户当前部门父级部门id集合
     * @param containSelf 是否包含当前部门
     * @return
     */
    List<String> getUserParentDeptIds(boolean containSelf);


    /**
     * 获取当前用户的父部门集合
     * @param containSelf 是否包含本身
     * @return
     */
    List<Department> getUserParentDepts(boolean containSelf);



    /**
     * 获得当前用户群组id集合
     * @return
     */
    List<String> getGroupIds();


    /**
     * 获取当前用户的群组
     * @return
     */
    List<Department> getGroupList();


}
