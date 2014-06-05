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
package xxx.yyy.sys.base.context;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xxx.yyy.sys.rbac.model.User;

/**
 * 系统变量工具类
 * 
 * @author serv
 *
 */
public class SystemContextHolder {

    private final static Logger log = LoggerFactory.getLogger(SystemContextHolder.class);

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    /**
     * 获取当前安全模型
     */
    public static SessionVariable getSessionContext(){
        try{
            Subject subject = SecurityUtils.getSubject();

            if (subject != null && subject.getPrincipal() != null && subject.getPrincipal() instanceof SessionVariable) {
                return (SessionVariable) subject.getPrincipal();
            }


        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return null;
    }

}
