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
import xxx.yyy.sys.security.model.Post;
import xxx.yyy.sys.security.repository.PostRepository;
import xxx.yyy.sys.security.service.PostService;

import java.util.List;

/**
 * Created by serv on 2014/6/2.
 */
@Service
@Transactional(readOnly = true)
public class PostServiceImpl extends BaseServiceImpl<Post> implements PostService {


    @Autowired
    private PostRepository postRepository;

    /**
     * 通过用户id获取所有岗位
     * @param userId 用户id
     * @return 岗位列表
     */
    public List<Post> getUserPosts(String userId){
        return postRepository.getUserPosts(userId);
    }

}
