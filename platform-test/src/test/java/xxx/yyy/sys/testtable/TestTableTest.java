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

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import xxx.yyy.sys.base.BaseTest;
import xxx.yyy.sys.datafilter.DataFilterType;
import xxx.yyy.sys.test.model.TestTable;
import xxx.yyy.sys.test.service.TestTableService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by serv on 14-5-30.
 */
public class TestTableTest extends BaseTest {

    @Autowired
    TestTableService testTableService;
    @Test
    public void insert(){
        TestTable t = new TestTable();
        t.setIid(0);
        t.setName("ddddf");
        t.setDeptId("fff");
        testTableService.save(t);
    }
    @Test
    public void list(){
        List<Integer> it = new ArrayList<>();
        it.add(0);
        it.add(1);
        long count = testTableService.count("iid in ?1 ",it);
        assertThat(count).isGreaterThan(0);
    }
    @Test
    public void testIn(){
        Iterable<TestTable> all = testTableService.findAll("x.iid in (select t.iid from TestTable t )");
        assertThat(all).isNotEmpty();
        testTableService.dataFilter(DataFilterType.READ).findAll();
        testTableService.findAll("name like ?1",new PageRequest(1,20),"%周扒皮%");
        testTableService.getOne("uuid");
        testTableService.queryUnDeleted().count();
    }


}
