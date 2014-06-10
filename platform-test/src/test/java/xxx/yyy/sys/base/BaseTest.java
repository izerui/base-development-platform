package xxx.yyy.sys.base; /**
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

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import xxx.yyy.sys.base.context.SessionVariable;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.rbac.repository.UserRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** 
 * @author  serv
 * @version createtime：2014年1月27日 下午2:12:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath*:application/applicationContext.xml",
        "classpath*:application/applicationContext-database.xml",
        "classpath*:application/*/*/applicationContext*.xml"})
@TransactionConfiguration(defaultRollback = false)
@TestExecutionListeners({
        DirtiesContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup(value = "classpath:DELETE_ALL.xml",type = DatabaseOperation.DELETE_ALL)
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {
	protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository userRepository;

    @Before
    public void setTestUser(){
        User user = userRepository.findOne("admin");

        Subject subjectUnderTest = mock(Subject.class);
        when(subjectUnderTest.getPrincipal()).thenReturn(new SessionVariable(user));
        subjectUnderTest.getPrincipal();
        ThreadContext.bind(subjectUnderTest);
    }

}
