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
package xxx.yyy.sys.rbac.service;

import xxx.yyy.sys.base.service.BaseService;
import xxx.yyy.sys.rbac.model.Post;

import java.util.List;

/**
 * Created by serv on 2014/6/2.
 */
public interface PostService extends BaseService<Post> {
    /**
     * 通过用户id获取所有岗位
     * @param userId 用户id
     * @return 岗位列表
     */
    public List<Post> getUserPosts(String userId);
}
