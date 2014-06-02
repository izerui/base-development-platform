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
package xxx.yyy.sys.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xxx.yyy.sys.base.service.BaseServiceImpl;
import xxx.yyy.sys.security.model.Resource;
import xxx.yyy.sys.security.repository.ResourceRepository;
import xxx.yyy.sys.security.service.ResourceService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by serv on 2014/6/2.
 */
@Service
@Transactional(readOnly = true)
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService {


    @Autowired
    private ResourceRepository resourceRepository;


    /**
     * 通过用户id获取用户所有角色包含的资源
     *
     * @param userId
     *            用户id
     *
     * @return List 资源列表
     */
    public List<Resource> getUserResources(String userId) {
        List<Resource> list = resourceRepository.getUserResources(userId);

        List<Resource> result = new ArrayList<Resource>();
        Set<String> ids = new HashSet<String>();
        for(Resource  r : list){
            if(ids.add(r.getId())){
                result.add(r);
            }
        }


        return result;
    }
}
