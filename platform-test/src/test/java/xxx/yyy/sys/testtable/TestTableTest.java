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
package xxx.yyy.sys.testtable;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.support.TransactionTemplate;
import xxx.yyy.sys.base.BaseTest;
import xxx.yyy.sys.datafilter.DataFilterType;
import xxx.yyy.sys.test.model.TestTable;
import xxx.yyy.sys.test.service.TestTableService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by serv on 14-5-30.
 */
public class TestTableTest extends BaseTest {

    @Autowired
    TestTableService testTableService;

    @Test
    public void insert() throws IOException {
        TestTable t = new  TestTable();
        t.setIid(0);
        t.setName("ddddf");
        t.setDeptId("ff");
        testTableService.dataFilter(DataFilterType.CREATE).save(t);
    }

    @Test
    @DatabaseSetup({"classpath:TEST_TABLE.xml","classpath:RBAC.xml"})
    public void list(){
        assertThat(testTableService.queryUnDeleted().findAll("id in ?1 ", Lists.newArrayList("1","2","3","4"))).hasSize(4);
        assertThat(testTableService.queryUnDeleted().dataFilter(DataFilterType.READ).findAll("id in ?1",Lists.newArrayList("1","2","3","4"))).hasSize(3);
    }

    @Test
    @DatabaseSetup({"classpath:TEST_TABLE.xml","classpath:RBAC.xml"})
    public void testCreate(){
        TestTable t  = new TestTable();
        t.setIid(2);
        t.setName("测试数据");
        //尝试注释掉下面一行 ， 则可以权限验证通过
        t.setDeptId("非本部门的数据");
        //会跑出异常，提示没有权限
        testTableService.dataFilter(DataFilterType.CREATE).save(t);
    }


}
