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
package xxx.yyy.framework.common.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.framework.common.enumeration.ValueEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 系统变量工具类
 * 
 * @author serv
 *
 */
@Component
@DependsOn("messageSource")
public class SystemVariableUtils {
	private static final Logger log = LoggerFactory.getLogger(SystemVariableUtils.class);
	
	static public String DEFAULT_DICTIONARY_VALUE = "dictionary.default.value";
	
	
	
	

	/**
	 * 获取当前用户Local信息   如果当前上下文中无用户这返回系统Locale
	 * @return
	 */
	public static Locale getUserLocal(){
		
		HttpServletRequest request = null;
		
		try{
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}catch(RuntimeException e){
			request = null;
		}

		
		SessionLocaleResolver localeResolver = SpringContextHolder.getBean("localeResolver");
 
		
		return  localeResolver.resolveLocale(request);
		
	}
	
	
	
	/**
	 * 通过字典枚举获取字典名称
	 * 
	 * @param enumClass 字典枚举class
	 * @param value 值
	 * 
	 * @return String
	 */
	public static String getMessage(Class<? extends Enum<? extends ValueEnum<?>>> enumClass,Object value) {
		
		
		MessageSource messageSource = SpringContextHolder.getBean("messageSource");
		if (value == null || enumClass == null) {
			return messageSource.getMessage(DEFAULT_DICTIONARY_VALUE, null, getUserLocal());
		}
		
		if (value instanceof String && StringUtils.isEmpty(value.toString())) {
			return messageSource.getMessage(DEFAULT_DICTIONARY_VALUE, null, getUserLocal());
		}
	
		Enum<?>[] values = enumClass.getEnumConstants();
		
		for (Enum<?> o : values) {
			ValueEnum<?> ve = (ValueEnum<?>) o;
 			if (StringUtils.equals(ve.getValue().toString(), value.toString())) {
				
				return messageSource.getMessage(ve.getName(), null, getUserLocal());
			}
			
		}
		
		return messageSource.getMessage(DEFAULT_DICTIONARY_VALUE, null, getUserLocal());
	}
	
	/**
	 * 通过code 获取 i18n 串
	 */
	public static String getMessage(String code,Object... args){
		MessageSource messageSource = SpringContextHolder.getBean("messageSource");
		return messageSource.getMessage(code, args, getUserLocal());
	}

	
	
	/**
	 * 获取当前安全模型
	 * 
	 * @return {@link SessionVariable}
	 */
	public static SessionVariable getSessionVariable() {
		try {
			Subject subject = SecurityUtils.getSubject();
			
			if (subject != null && subject.getPrincipal() != null && subject.getPrincipal() instanceof SessionVariable) {
				return (SessionVariable) subject.getPrincipal();
			}
			
		} catch (UnavailableSecurityManagerException e) {
			log.debug(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 判断当前会话是否登录
	 * 
	 * @return boolean
	 */
	public static boolean isAuthenticated() {
		return SecurityUtils.getSubject().isAuthenticated();
	}
	
//	public static boolean isShowCaptcha(){
//		Object obj = SecurityUtils.getSubject().getSession().getAttribute(CaptchaAuthenticationFilter.DEFAULT_SHOW_CAPTCHA_KEY_ATTRIBUTE);
//		return obj==null?false:(Boolean)obj;
//	}
	
}
