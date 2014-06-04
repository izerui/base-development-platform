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
package xxx.yyy.sys.rbac.service;

import xxx.yyy.sys.base.service.BaseService;
import xxx.yyy.sys.rbac.model.Department;

import java.util.List;

/**
 * Created by serv on 2014/6/2.
 */
public interface DepartmentService extends BaseService<Department> {


    /**
     * 获取顶级机构
     * @param deptId
     * @return
     */
    public  Department findRootDept(String deptId);


    /**
     * 获取所有父级的列表
     * @param deptId 部门id
     * @param containSelf 是否包含本身
     * @param ignoreType 忽略的类型 {@link xxx.yyy.framework.common.enumeration.DepartmentType}  多个以 , 号区分
     * @return
     */
    public List<Department> getAllParent(String deptId,boolean containSelf,String ignoreType);


}
