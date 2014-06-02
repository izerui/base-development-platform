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
package xxx.yyy.sys.record;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import xxx.yyy.framework.common.enumeration.State;
import xxx.yyy.sys.base.BaseTest;
import xxx.yyy.sys.base.model.OperatingRecord;
import xxx.yyy.sys.base.repository.OperatingRecordDao;
import xxx.yyy.sys.security.repository.RoleRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by serv on 14-5-29.
 */
public class RecordTest extends BaseTest {

    @Autowired
    OperatingRecordDao operatingRecordDao;

    @Autowired
    RoleRepository roleRepository;


    @Before
    public void insert(){
        OperatingRecord op = new OperatingRecord();
        op.setEndDate(new Date());
        op.setIp("dddd");
        op.setModule("凤飞飞");
        op.setUsername("ddjdjf");
        op.setMethod("djdjd");
        op.setOperatingTarget("djdjdj");
        op.setStartDate(new Date());
        op.setState(State.Enable.getValue());
        operatingRecordDao.save(op);
        List<OperatingRecord> all = operatingRecordDao.findAll();
        assertThat(all).isNotEmpty();
    }


    @Test
    public void searData(){
        assertThat(operatingRecordDao.findAll(" ip is not null")).isNotEmpty();
        assertThat(operatingRecordDao.findAll(" x.ip is not null")).isNotEmpty();
        assertThat(operatingRecordDao.findAll(" x.ip = ?1","dddd")).isNotEmpty();
        assertThat(operatingRecordDao.findAll(" ip = ?1 and x.method = ?2","dddd","djdjd")).isNotEmpty();
        assertThat(operatingRecordDao.count(" ip = ? and x.method = ?","dddd","djdjd")).isGreaterThan(0);

        Set set = new HashSet<>();
        set.add("dddd");

        assertThat(operatingRecordDao.findAll(" ip in ?1 and x.method = ?2",set,"djdjd")).isNotEmpty();


        Pageable pageable = new PageRequest(0,3);
        Page<OperatingRecord> djdjd = operatingRecordDao.findAll(" ip in ?1 and x.method = ?2", pageable, set, "djdjd");
        assertThat(djdjd).isNotEmpty();

        assertThat(roleRepository.queryUnDeleted().findAll("name = ?1","dhh")).isEmpty();
        assertThat(roleRepository.getUserRoles("23")).isEmpty();


    }



}
