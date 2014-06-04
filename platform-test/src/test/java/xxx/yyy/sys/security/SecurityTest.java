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

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xxx.yyy.sys.base.BaseTest;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.rbac.service.AccountService;

/**
 * Created by serv on 2014/6/2.
 */
public class SecurityTest extends BaseTest {

    @Autowired
    AccountService accountService;

    @Test
    public void testSelectService(){
        accountService.queryDeleted().findAll();

    }

    @Test
    public void addAdmin(){
        User user = new User();
        user.setId("admin");
        user.setName("admin");
        user.setPassword("21232f297a57a5a743894a0e4a801fc3");
        user.setEmail("ddjdjj");
        accountService.save(user);
    }



    @Test
    public void testInsert(){
        User user2 = new User();
        user2.setName("ddd");
        user2.setState(1);
        user2.setEmail("fff");
        user2.setPassword("ddddfsdfsdfsdfsdf");
        User user = new User();
        user.setName("ddd");
        user.setState(1);
        user.setEmail("fff");
        user.setPassword("ddddfsdfsdfsdfsdf");
        accountService.save(Lists.newArrayList(user,user2));
    }
}
