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
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import xxx.yyy.sys.rbac.model.Post;
import xxx.yyy.sys.rbac.model.User;

import java.util.Collection;

/**
 * 岗位上下文
 * Created by serv on 2014/6/2.
 */
public class PostContext extends AbstractUserContext{


    private Collection<Post> postList;


    public PostContext(User user) {
        super(user);
    }

    @Override
    protected void init() {
        postList = Collections2.filter(getUser().getPosts(),new Predicate<Post>() {
            @Override
            public boolean apply(Post input) {
                return !input.isDeleteStatus();
            }
        });
    }

    /**
     * 获取当前用户岗位列表
     *
     * @return
     */
    public Collection<String> getPostIds() {
        return Collections2.transform(postList, new Function<Post, String>() {
            @Override
            public String apply(Post input) {
                return input.getId();
            }
        });
    }

    /**
     * 获取当前用户岗位列表
     *
     * @return
     */
    public Collection<Post> getPostList() {
        return postList;
    }
}
