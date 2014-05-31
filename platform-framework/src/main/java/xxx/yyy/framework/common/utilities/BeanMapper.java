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

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory.Builder;

/** 
 * @author  izerui.com
 * @version createtime：2014年1月6日 下午4:23:16 
 */
public class BeanMapper extends ConfigurableMapper {
	@Override
	protected void configure(MapperFactory factory) {
//		factory
	}
	
	@Override
	protected void configureFactoryBuilder(Builder factoryBuilder) {
		factoryBuilder.mapNulls(false);
	}
}
