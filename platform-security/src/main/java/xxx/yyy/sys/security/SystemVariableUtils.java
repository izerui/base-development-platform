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
package xxx.yyy.sys.security;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import xxx.yyy.sys.security.filter.CaptchaAuthenticationFilter;

/**
 * 系统变量工具类
 * 
 * @author serv
 *
 */
public class SystemVariableUtils{

	
	/**
	 * 判断当前会话是否登录
	 * 
	 * @return boolean
	 */
	public static boolean isAuthenticated() {
		return SecurityUtils.getSubject().isAuthenticated();
	}
	
	public static boolean isShowCaptcha(){
		Object obj = SecurityUtils.getSubject().getSession().getAttribute(CaptchaAuthenticationFilter.DEFAULT_SHOW_CAPTCHA_KEY_ATTRIBUTE);
		return obj==null?false:(Boolean)obj;
	}
	
}
