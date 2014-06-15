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
package xxx.yyy.sys.testtable.repository;

import org.springframework.stereotype.Repository;
import xxx.yyy.sys.base.jpa.PlatformJpaRepository;
import xxx.yyy.sys.testtable.model.TestTable;

import java.util.List;

/**
 * Created by serv on 14-5-30.
 */
@Repository
public interface TestTableRepository extends PlatformJpaRepository<TestTable>{

    public List<TestTable> findByIid(Integer id);
}
