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

import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.sys.rbac.model.Resource;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.rbac.service.ResourceService;

import java.util.List;

/**
 * Created by serv on 2014/6/2.
 */
public class ResourceContext extends AbstractUserContext{


    private List<Resource> resourceList;

    public ResourceContext(User user) {
        super(user);
    }

    @Override
    protected void init() {
        resourceList = SpringContextHolder.getBean(ResourceService.class).getUserResources(getUser().getId());
    }

    /**
     * 用户具有的资源
     *
     * @return
     */
    public List<Resource> getResourceList() {
        return resourceList;
    }


}
