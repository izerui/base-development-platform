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
package xxx.yyy.sys.security.service;

import xxx.yyy.sys.base.service.BaseService;
import xxx.yyy.sys.security.model.Role;
import xxx.yyy.sys.security.model.User;

import java.util.List;

/**
 * Created by serv on 2014/6/1.
 */
public interface AccountService extends BaseService<User> {


    /**
     * 通过用户id获取所有角色
     * @param userId 用户id
     * @return {@link java.util.List}
     */
    public List<Role> getUserRoles(String userId);


    /**
     * 获取所有角色信息
     * @return List
     */
    public List<Role> getRoles();

}
