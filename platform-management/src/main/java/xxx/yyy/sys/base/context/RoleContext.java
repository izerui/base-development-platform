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
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.sys.rbac.model.Role;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.rbac.service.AccountService;

import java.util.Collection;
import java.util.List;

/**
 * 角色上下文
 * Created by serv on 2014/6/2.
 */
public class RoleContext extends AbstractUserContext{

    //系统管理员角色名
    private final static String SYSTEM_SUPER_ADMIN_ROLE_NAME = "system";

    private List<Role> roleList;

    public RoleContext(User user) {
        super(user);
    }

    @Override
    protected void init() {
        roleList = SpringContextHolder.getBean(AccountService.class).getOne(getUser().getId()).getRoles();
    }

    /**
     * 角色验证
     *
     * @param role
     * @return 当前用户是否拥有某角色
     */
    public boolean checkRole(String role) {
        return Iterables.contains(this.geRoleNames(),role);
    }


    /**
     * 获取角色名称列表
     *
     * @return
     */
    public Collection<String> geRoleNames() {
        return Collections2.transform(roleList,new Function<Role, String>() {
            @Override
            public String apply(Role input) {
                return input.getName();
            }
        });
    }


    /**
     * 获取角色ID列表
     *
     * @return
     */
    public Collection<String> getRoleIds() {
        return Collections2.transform(roleList,new Function<Role, String>() {
            @Override
            public String apply(Role input) {
                return input.getId();
            }
        });
    }
    /**
     * 获取角色名称列表
     *
     * @return
     */
    public Collection<String> getRoleNames() {
        Collection<String> roleNames = Collections2.transform(roleList,new Function<Role, String>() {
            @Override
            public String apply(Role input) {
                return input.getName();
            }
        });
        return Collections2.filter(roleNames, Predicates.notNull());
    }

    /**
     * 获取当前用户的角色列表
     *
     * @return
     */
    public List<Role> getRolesList() {
        return roleList;
    }

    /**
     * 判断当前用户是否为系统超级管理员
     *
     * @return
     */
    public boolean isSystem() {
        return checkRole(SYSTEM_SUPER_ADMIN_ROLE_NAME);
    }

}
