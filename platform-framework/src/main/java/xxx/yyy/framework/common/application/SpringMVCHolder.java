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
package xxx.yyy.framework.common.application;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * spring mvc 上下文持有者，类似Struts2的ServletActionContext,
 * 
 * @author izerui.com
 *
 */
@SuppressWarnings("unchecked")
public abstract class SpringMVCHolder {
	
	/**
	 * 获取request attribute
	 * 
	 * @param name 属性名称
	 * 
	 * @return Object
	 */
	public static <T> T getRequestAttribute(String name) {
		return (T)getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}
	
	/**
	 * 设置request attribute
	 * 
	 * @param name 属性名称
	 * @param value 值
	 */
	public static void addRequestAttribute(String name,Object value) {
		addAttribute(name, value, RequestAttributes.SCOPE_REQUEST);
	}
	
	/**
	 * 删除request attribute
	 * 
	 * @param name 属性名称
	 */
	public void removeRequestAttribute(String name) {
		removeAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * 获取sessiont attribute
	 * 
	 * @param name 属性名称
	 * 
	 * @return Object
	 */
	public static <T> T getSessionAttribute(String name) {
		return (T)getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	/**
	 * 设置session attribute
	 * 
	 * @param name 属性名称
	 * @param value 值
	 */
	public static void addSessionAttribute(String name,Object value) {
		addAttribute(name, value, RequestAttributes.SCOPE_SESSION);
	}
	
	/**
	 * 删除session attribute
	 * 
	 * @param name 属性名称
	 */
	public void removeSessionAttribute(String name) {
		removeAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	/**
	 * 根据作用域,获取Attribute
	 * 
	 * @param name attribute名称
	 * @param scope 作用域,参考{@link org.springframework.web.context.request.RequestAttributes}
	 *
	 * @return Object
	 */
	public static <T> T  getAttribute(String name,int scope) {
		return (T) getServletRequestAttributes().getAttribute(name, scope);
	}

	/**
	 * 根据作用域,设置Attribute
	 *
	 * @param name attribute名称
	 * @param value 值
	 * @param scope 作用域,参考{@link org.springframework.web.context.request.RequestAttributes}
	 */
	public static void  addAttribute(String name,Object value, int scope) {
		getServletRequestAttributes().setAttribute(name, value, scope);
	}

	/**
	 * 根据作用域和名称，删除Attribute
	 *
	 * @param name attribute名称
	 * @param scope 作用域,参考{@link org.springframework.web.context.request.RequestAttributes}
	 */
	public static void removeAttribute(String name,int scope) {
		getServletRequestAttributes().removeAttribute(name, scope);
	}

	/**
	 * 获取HttpServletRequest
	 *
	 *
	 * @return {@link HttpServletRequest}
	 */
	public static HttpServletRequest  getRequest() {

		return getServletRequestAttributes().getRequest();
	}

	/**
	 * 获取 http session
	 *
	 * @return {@link HttpSession}
	 */
	public static HttpSession getSession() {
		return getSession(false);
	}

	/**
	 * 获取 http session
	 *
	 * @param create true to create a new session for this request if necessary; false to return null if there's no current session
	 *
	 * @return {@link HttpSession}
	 */
	public static HttpSession getSession(boolean create) {
		return getRequest().getSession(create);
	}

	/**
	 * 获取spring mvc的ServletRequestAttributes
	 *
	 * @return {@link org.springframework.web.context.request.ServletRequestAttributes}
	 */
	public static ServletRequestAttributes getServletRequestAttributes() {
		return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	}
	
	/**
	 * 获取web项目中的真实路径
	 * 
	 * @param path 指定的虚拟路径
	 * 
	 * @return String
	 */
	public static String getRealPath(String path) {
		return getRequest().getSession().getServletContext().getRealPath(path);
	}
}
