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
package xxx.yyy.sys.security.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xxx.yyy.sys.base.service.BaseServiceImpl;
import xxx.yyy.sys.security.model.Department;
import xxx.yyy.sys.security.service.DepartmentService;

import java.util.List;

/**
 * Created by serv on 2014/6/2.
 */
@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {

    /**
     * 获取顶级机构
     * @param deptId
     * @return
     */
    public  Department findRootDept(String deptId){

        Department dept = findOne(deptId);
        if(dept==null){
            return null;
        }

        while(dept.getOrgId()!=null&&(!dept.getId().equals(dept.getOrgId()))){
            dept = findOne(dept.getOrgId());
        }

        return dept;
    }

    @Override
    public List<Department> getAllChildDepts(String deptId, boolean containsSelf, String ignoreType) {
        return findOne(deptId).mergerTolist(containsSelf, ignoreType);
    }

    @Override
    public List<Department> getTree(String orgId, boolean containsSelf, String ignoreType) {
        return findOne(orgId).formatToTree(containsSelf,ignoreType);
    }

    @Override
    public List<Department> getAllParent(String deptId, boolean containSelf, String ignoreType) {
        List<Department> allParent = Lists.newArrayList();
        Department dept = findOne(deptId);
        if(containSelf){
            allParent.add(dept);
        }
        applyParentList(dept.getParent(),allParent,ignoreType);
        return allParent;
    }

    private void applyParentList(Department department,List<Department> list,String ignoreType){

        if(department==null){
            return;
        }
        if(ignoreType==null|| !ArrayUtils.contains(StringUtils.split(ignoreType,","),ignoreType)){
            list.add(department);
            if(department.getParent()!=null){
                applyParentList(department.getParent(),list,ignoreType);
            }
        }

    }

}
