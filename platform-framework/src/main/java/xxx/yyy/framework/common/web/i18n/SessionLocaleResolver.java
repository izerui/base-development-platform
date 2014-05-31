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
package xxx.yyy.framework.common.web.i18n;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


/**
 * 国际化资源解析器
 * 
 * @author serv
 *
 */
public class SessionLocaleResolver extends org.springframework.web.servlet.i18n.SessionLocaleResolver{
	
	
	/***
	 * 获取默认Locale信息
	 */
	public Locale getDefaultLocale() {
		return super.getDefaultLocale();
	}
	
	
	/***
	 * 
	 */
	public Locale resolveLocale(HttpServletRequest request) {
		
		if(request==null){
			return this.resolveLocale();
		}
		
		Locale locale = super.resolveLocale(request);
		if(locale==null){
			locale = this.resolveLocale();
		}
		return locale;
	}
	
	public Locale resolveLocale() {
		Locale locale = this.getDefaultLocale();
		if(locale==null){
			locale = LocaleContextHolder.getLocale();
		}
		if(locale==null){
			locale =  Locale.CHINA;
		}
		return locale;
	}
	
	
}
