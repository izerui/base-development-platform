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
import org.apache.commons.lang3.StringUtils;
import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.framework.common.enumeration.DepartmentType;
import xxx.yyy.sys.rbac.model.Department;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.rbac.service.DepartmentService;

import java.util.Collection;
import java.util.List;

/**
 * 单位上下文
 * Created by serv on 14-6-5.
 */
public class OrgContext extends AbstractDeptTriangleContext {

    private final static String IGNORE_DEPT_GROUP = DepartmentType.DEPARTMENT.getValue()+","+DepartmentType.GROUP.getValue();

    //用户的最上层单位
    private Department rootOrg;
    //当前单位
    private Department org;

    //包含当前单位和子单位的集合
    private List<Department> childOrgList;

    public OrgContext(User user) {
        super(user);
    }

    @Override
    protected void init() {
        super.init();
        rootOrg = SpringContextHolder.getBean(DepartmentService.class).findRootDept(this.getOrgId());
        if(StringUtils.isNotEmpty(getOrgId())){
            childOrgList = SpringContextHolder.getBean(DepartmentService.class).getOne(getOrgId()).mergerTolist(true,IGNORE_DEPT_GROUP);

        }
        org = Iterables.find(childOrgList,new Predicate<Department>() {
            @Override
            public boolean apply(Department input) {
                return StringUtils.equals(input.getId(), getOrgId());
            }
        });

    }

    /**
     * 获取登录用户当前机构子机构id集合
     *
     * @return
     */
    public Collection<String> getChildOrgIds() {
        return Collections2.transform(childOrgList, new Function<Department, String>() {
            @Override
            public String apply(Department input) {
                return input.getId();
            }
        });
    }

    /**
     * 获取用的当前单位和子单位集合
     *
     * @return
     */
    public List<Department> getChildOrgs() {
        return childOrgList;
    }

    /**
     * 获取当前登录人员所在最上级单位
     *
     * @return
     */
    public Department getRootOrg() {
        return rootOrg;
    }

    /**
     * 获取当前登录人员所在顶级单位id
     *
     * @return
     */
    public String getRootOrgId() {
        return rootOrg.getId();
    }

    /**
     * 获取当前登录人员所在单位
     * @return
     */
    public Department getOrg(){

        return org;
    }

    /**
     * 获取当前登录人员所在单位
     * @return
     */
    public String getOrgId(){

        return getUser().getOrgId();
    }




}
