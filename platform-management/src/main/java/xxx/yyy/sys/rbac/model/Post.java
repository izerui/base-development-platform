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
package xxx.yyy.sys.rbac.model;

import xxx.yyy.sys.base.model.BaseInfoModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 岗位
 * @author serv
 *
 */
@Entity
@Table(name="SYS_RBAC_POST")
public class Post extends BaseInfoModel {
	

	/**
	 * 岗位人员列表
	 */
    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(name = "SYS_RBAC_POST_USER", joinColumns = { @JoinColumn(name = "SYS_RBAC_POST_ID") }, inverseJoinColumns = { @JoinColumn(name = "SYS_RBAC_USER_ID") })
	protected List<User> users = new ArrayList<User>() ;
	

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the usersList to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	 
	
	
}
