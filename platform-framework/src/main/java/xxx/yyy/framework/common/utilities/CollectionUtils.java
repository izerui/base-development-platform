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

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


/**
 * 泛型工具类
 * 
 * @author izerui.com
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils{
	
	/**
	 * 提取集合中的对象的属性(通过Getter函数), 组合成Map.
	 * 
	 * @param collection 来源集合.
	 * @param keyPropertyName 要提取为Map中的Key值的属性名.
	 * @param valuePropertyName 要提取为Map中的Value值的属性名.
	 */
	public static Map extractToMap(Collection collection, String keyPropertyName, String valuePropertyName) {
		Map map = new HashMap();

		try {
			for (Object obj : collection) {
				map.put(PropertyUtils.getProperty(obj, keyPropertyName), PropertyUtils.getProperty(obj, valuePropertyName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 提取集合中的对象的属性(通过Getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * 
	 * @return List
	 */
	public static <T> List<T> extractToList(Collection collection, String propertyName) {
		
		return extractToList(collection,propertyName,false);
	}
	
	/**
	 * 提取集合中的对象的属性(通过Getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param ignoreEmptyValue 是否过滤null值和""值
	 * 
	 * @return List
	 */
	public static <T> List<T> extractToList(Collection collection, String propertyName,boolean ignoreEmptyValue) {
		if (collection == null) {
			return null;
		}
		List list = new ArrayList();
		
		try {
			for (Object obj : collection) {
				T value = (T) PropertyUtils.getProperty(obj, propertyName);
				if (ignoreEmptyValue && value == null || "".equals(value)) {
					continue;
				}
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过Getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	public static String extractToString(Collection collection, String propertyName, String separator) {
		List list = extractToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

    /**
     * 转换list到另一个对象集合(深层拷贝)
     * @param list 要转换的list集合
     * @param clas 单个元素要转换为的Class类型
     * @return 转换后的集合
     */
    public static <T extends Object,S extends Object> List<T> convertListToList(List<S> list, Class<T> clas){
        if(null == list){
            return null;
        }
        List<T> targetList = new ArrayList<T>();
        for (S source : list) {
            targetList.add(new BeanMapper().map(source, clas));
        }
        return targetList;
    }
}
