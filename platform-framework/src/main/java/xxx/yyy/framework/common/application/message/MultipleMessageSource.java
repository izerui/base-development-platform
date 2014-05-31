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
package xxx.yyy.framework.common.application.message;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * @author serv
 * @version createtime：2014年1月27日 下午1:06:45
 */
public class MultipleMessageSource extends ReloadableResourceBundleMessageSource {
	private static final String PROPERTIES_SUFFIX = ".properties";
	private ResourcePatternResolver  resolver = new PathMatchingResourcePatternResolver();

	@Override
	protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
		Properties properties = new Properties();
		long lastModified = -1;
		try {
			Resource[] resources = resolver.getResources(filename + "*" +PROPERTIES_SUFFIX);
			for (Resource resource : resources) {
				String sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
				PropertiesHolder holder = super.refreshProperties(sourcePath, propHolder);
				properties.putAll(holder.getProperties());
				if (lastModified < resource.lastModified())
					lastModified = resource.lastModified();
			}
		} catch (IOException ignored) {
		}
		return new PropertiesHolder(properties, lastModified);
	}
}
