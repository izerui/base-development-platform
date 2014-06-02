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
package xxx.yyy.sys.security.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xxx.yyy.framework.jpa.PlatformJpaRepository;
import xxx.yyy.sys.security.model.Resource;

import java.util.List;

/**
 * Created by serv on 2014/6/1.
 */
@Repository
public interface ResourceRepository extends PlatformJpaRepository<Resource,String> {

    /**
     * 通过用户id获取用户所有角色包含的资源
     * @param userId 用户id
     * @return List 资源列表
     */
    @Query("select rl from User  u join u.roles gl  join gl.resources rl   where u.id=?1  order by rl.sort")
    public List<Resource> getUserResources(String userId);

}
