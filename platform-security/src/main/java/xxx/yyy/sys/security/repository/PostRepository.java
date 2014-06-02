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

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xxx.yyy.framework.jpa.PlatformJpaRepository;
import xxx.yyy.sys.security.model.Post;

import java.util.List;

/**
 * Created by serv on 2014/6/1.
 */
@Repository
public interface PostRepository extends PlatformJpaRepository<Post,String> {

    /**
     * 通过机构获取下面的岗位
     * @param orgId
     * @param sort
     * @return
     */
    @Query("from Post p where p.deleteStatus=false and  p.orgId=?1 ")
    public List<Post> findPostByOrgId(String orgId,Sort sort);


    /**
     * 通过用户id获取所有岗位
     * @param userId 用户id
     * @return 岗位列表
     */
    @Query("select gl from User u left join u.posts gl  where   u.id=?1 and gl.deleteStatus=false")
    public List<Post> getUserPosts(String userId);

}
