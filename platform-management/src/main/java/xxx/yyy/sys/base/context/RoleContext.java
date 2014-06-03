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

import xxx.yyy.sys.rbac.model.Role;

import java.util.List;

/**
 * 角色上下文
 * Created by serv on 2014/6/2.
 */
public interface RoleContext {

    /**
     * 角色验证
     * @param role
     * @return 当前用户是否拥有某角色
     */
    boolean checkRole(String role);


    /**
     * 获取角色名称列表
     * @return
     */
    List<String> geRoleNames();


    /**
     * 获取角色ID列表
     * @return
     */
    List<String> getRoleIds();

    /**
     * 获取当前用户的角色列表
     * @return
     */
    List<Role> getRolesList();

}
