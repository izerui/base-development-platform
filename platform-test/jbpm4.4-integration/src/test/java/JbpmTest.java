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

import org.assertj.core.api.Assertions;
import org.jbpm.api.RepositoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created by serv on 2014/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath*:application/applicationContext*.xml",
        "classpath*:application/*/applicationContext*.xml"})
@TransactionConfiguration(defaultRollback = false)
public class JbpmTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    RepositoryService repositoryService;

    @Test
    public void testStart(){

        deleteFromTables("JBPM4_LOB","JBPM4_DEPLOYMENT");

        repositoryService.createDeployment().addResourceFromClasspath("jpdl.xml").setName("jpdl-demo").deploy();

        Assertions.assertThat(repositoryService.createDeploymentQuery().list()).hasSize(1);

        System.out.println("jbpm init");
    }
}
