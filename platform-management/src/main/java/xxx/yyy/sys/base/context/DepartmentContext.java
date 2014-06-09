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

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.framework.common.enumeration.DepartmentType;
import xxx.yyy.sys.rbac.model.Department;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.rbac.service.DepartmentService;

import java.util.Collection;
import java.util.List;

/**
 * 部门上下文
 * Created by serv on 2014/6/2.
 */
public class DepartmentContext extends AbstractDeptTriangleContext{


    private final static String IGNORE_ORG_GROUP = DepartmentType.ORGANIZATION.getValue()+","+DepartmentType.GROUP.getValue();

    //包含自身和所有子部门的列表
    private List<Department> childList;
    protected Department defaultDepartment;
    protected List<Department> containsParentList;

    public DepartmentContext(User user) {
        super(user);
    }

    @Override
    protected void init() {
        super.init();
        childList = Lists.newArrayList();
        containsParentList = Lists.newArrayList();

        //循环当前用户的多个关联部门， 分别获取部门的子部门，并且统一合并到 childList中 （当前用户的所有关联部门和子部门的合集）
        for(Department dept : getIgnoreTypeList(IGNORE_ORG_GROUP)){
            childList.addAll(SpringContextHolder.getBean(DepartmentService.class).getOne(dept.getId()).mergerTolist(true,IGNORE_ORG_GROUP));
        }
        //默认部门id 肯定属于 对应的 关联的多个部门id 之一，如果在此之外则 属于数据错误。返回即为null ,因为群组id和部门id是唯一，所以就不忽略查询了
        defaultDepartment = Iterables.find(getDepartmentList(),new Predicate<Department>() {
            @Override
            public boolean apply(Department input) {
                return StringUtils.equals(input.getId(),getUser().getDefaultDeptId());
            }
        });
        //获取所有父级的部门列表
        for(Department department: getDepartmentList()){
            containsParentList.addAll(SpringContextHolder.getBean(DepartmentService.class).getAllParent(department.getId(), true, IGNORE_ORG_GROUP));
        }
    }

    /**
     * 获取用户的默认部门
     *
     * @return
     */
    public Department getDefaultDept() {
        return defaultDepartment;
    }

    /**
     * 获取用户的默认部门ID
     *
     * @return
     */
    public String getDefaultDeptId() {
        return null==defaultDepartment?null:defaultDepartment.getId();
    }



    /**
     * 获取登录用户当前部门id集合
     *
     * @return
     */
    public Collection<String> getDepartmentIds() {
        return Collections2.transform(getDepartmentList(), new Function<Department, String>() {
            @Override
            public String apply(Department input) {
                return input.getId();
            }
        });
    }

    /**
     * 获取登录用户当前部门集合
     *
     * @return
     */
    public Collection<Department> getDepartmentList() {
        return getIgnoreTypeList(DepartmentType.GROUP.getValue());
    }


    /**
     * 获取登录用户当前部门和子部门id集合
     *
     * @return
     */
    public Collection<String> getChildDepartmentIds() {
        return Collections2.transform(childList, new Function<Department, String>() {
            @Override
            public String apply(Department input) {
                return input.getId();
            }
        });
    }


    /**
     * 获取登录用户当前部门和子部门集合
     *
     * @return
     */
    public List<Department> getChildDepartments() {
        return childList;
    }




    /**
     * 获取登录用户当前部门和父级部门id集合
     *
     * @return
     */
    public Collection<String> getContainsParentIds() {
        return Collections2.transform(containsParentList,new Function<Department, String>() {
            @Override
            public String apply(Department input) {
                return input.getId();
            }
        });
    }


    /**
     * 获取当前用户部门和父部门集合
     *
     * @return
     */
    public List<Department> getContainsParentList() {
        return containsParentList;
    }




}
