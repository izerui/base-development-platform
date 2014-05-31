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
package xxx.yyy.framework.common.utilities;

import org.apache.commons.beanutils.converters.DateConverter;

import java.util.Date;

/**
 * 类型转换工具类
 * 
 */
public class ConvertUtils extends org.apache.commons.beanutils.ConvertUtils{
	
	static {
		registerDateConverter("yyyy-MM-dd");
	}
	
	/**
	 * 注册一个时间类型的转换器,当前默认的格式为：yyyy-MM-dd
	 * 
	 * @param patterns 日期格式
	 */
	public static void registerDateConverter(String... patterns) {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(patterns);
		register(dc, Date.class);
	}
	
	/**
	 * 基于Apache BeanUtils转换字符串到相应类型.
	 * 
	 * @param value 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convertToObject(String value, Class<?> toType) {
		try {
			return convert(value, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
	
	/**
	 * 转换字符串数组到相应类型.
	 * 
	 * @param values 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convertToObject(String[] values,Class<?> toType) {
		try {
			return convert(values, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
}
