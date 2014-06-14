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

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import xxx.yyy.sys.rbac.model.Department;
import xxx.yyy.sys.rbac.model.User;

import java.util.Collection;
import java.util.List;

/**
 * 群组、单位、部门上下文抽象类
 * Created by serv on 14-6-5.
 */
public abstract class AbstractDeptTriangleContext extends AbstractUserContext{


    private List<Department> departmentList;

    @Override
    protected void init() {
        departmentList = getUser().getDeptList();
    }


    public AbstractDeptTriangleContext(User user) {
        super(user);
    }


    /**
     * 获取当前用户忽略类型的集合
     * @param ignoreTypes
     * @return
     */
    protected Collection<Department> getIgnoreTypeList(final String ignoreTypes){
        Collection<Department> result = Collections2.transform(departmentList,new Function<Department, Department>() {
            @Override
            public Department apply(Department input) {
                if (ignoreTypes == null || !ArrayUtils.contains(StringUtils.split(","), input.getType())) {
                    return input;
                }
                return null;
            }
        });
        return Collections2.filter(result, Predicates.notNull());
    }


}
