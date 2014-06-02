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
package xxx.yyy.sys.security;

import xxx.yyy.sys.security.model.Post;

import java.util.List;

/**
 * 岗位上下文
 * Created by serv on 2014/6/2.
 */
public interface PostContext {
    /**
     * 获取当前用户岗位列表
     * @return
     */
    List<String> getPostIds();
    /**
     * 获取当前用户岗位列表
     * @return
     */
    List<Post> getPostList() ;
}
