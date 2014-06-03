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
package xxx.yyy.sys.rbac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xxx.yyy.sys.base.service.BaseServiceImpl;
import xxx.yyy.sys.rbac.model.Role;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.rbac.repository.RoleRepository;
import xxx.yyy.sys.rbac.repository.UserRepository;
import xxx.yyy.sys.rbac.service.AccountService;

import java.util.List;

/**
 * @author serv
 * @version createtime：2014-5-31
 */
@Service
@Transactional(readOnly = true)
public class AccountServiceImpl extends BaseServiceImpl<User> implements AccountService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;


	public List<Role> getUserRoles(String userId) {
		return roleRepository.getUserRoles(userId);
	}


	public List<Role> getRoles() {
		return roleRepository.queryUnDeleted().findAll();
	}

}
