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
package xxx.yyy.sys.shiro.realm;

import com.xcysoft.boss.sys.base.enumeration.State;
import com.xcysoft.boss.sys.security.SessionVariable;
import com.xcysoft.boss.sys.security.service.AccountService;
import com.xcysoft.boss.sys.user.model.User;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * apache shiro 的身份验证类
 * 
 * @author izerui.com
 *
 */
public class JdbcAuthenticationRealm extends AuthorizationRealm{
	
	@Autowired
	private AccountService userService;

	/**
	 * 用户登录的身份验证方法
	 * 
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String username = usernamePasswordToken.getUsername();
        
        if (username == null) {
            throw new AccountException("用户名不能为空");
        }
        
        User user = userService.getUserById(username);
        
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        
        if (user.getState().equals(State.Disable.getValue())) {
        	 throw new DisabledAccountException("你的账户已被禁用,请联系管理员开通.");
        }
        
        SessionVariable model = new SessionVariable(user);
        
        return new SimpleAuthenticationInfo(model,user.getPassword(),getName());
	}
	

}
