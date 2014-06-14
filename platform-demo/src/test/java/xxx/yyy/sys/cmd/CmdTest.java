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
package xxx.yyy.sys.cmd;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xxx.yyy.sys.base.BaseTest;
import xxx.yyy.sys.base.jpa.cmd.Command;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.testtable.service.TestTableService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by serv on 14-6-14.
 */
public class CmdTest extends BaseTest {

    @Autowired
    TestTableService testTableService;


    @Test
    public void testCMD(){
        testTableService.executeCommand(new Command() {
            @Override
            public Object execute(EntityManager entityManager) {
                Query query = entityManager.createQuery("select u from User u");
                List<User> resultList = query.getResultList();
                assertThat(resultList).have(new Condition() {
                    @Override
                    public boolean matches(Object value) {
                        User user = (User) value;
                        return user.getId().equals("admin");
                    }
                });
                log.info("测试数据 ：{}",resultList.toString());
                return null;
            }
        });
    }

}
