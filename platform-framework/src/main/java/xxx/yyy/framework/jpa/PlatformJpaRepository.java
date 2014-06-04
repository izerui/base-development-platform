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
package xxx.yyy.framework.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import xxx.yyy.framework.jpa.cmd.Command;

import java.io.Serializable;
import java.util.List;

/**
 * Created by serv on 14-5-29.
 */
@NoRepositoryBean
public interface PlatformJpaRepository<T,ID extends Serializable> extends JpaRepository<T,ID> {


    /**
     * 附加单位数据约束
     * @param orgId
     * @return
     */
    PlatformJpaRepository<T,ID> queryOrgId(String orgId);


    /**
     * 过滤数据权限
     * @param operationType {@see xxx.yyy.sys.datafilter.OperationType}
     * @return
     */
    PlatformJpaRepository<T,ID> dataFilter(String operationType);

    /**
     * 未标志删除状态的数据
     * @return
     */
    PlatformJpaRepository<T,ID> queryUnDeleted();

    /**
     * 已经标志为删除状态的数据
     * @return
     */
    PlatformJpaRepository<T,ID> queryDeleted();

    /**
     * Returns a single entity matching the given condition.
     *
     * @param condition
     * @return
     */
    T findOne(String condition , Object... objects);

    /**
     * Returns all entities matching the given condition.
     *
     * @param condition
     * @return
     */
    List<T> findAll(String condition , Object... objects);

    /**
     * Returns all entities matching the given condition applying the given {@link org.springframework.data.domain.Sort}s.
     *
     * @param condition
     * @param sort
     * @return
     */
    List<T> findAll(String condition, Sort sort , Object... objects);

    /**
     * Returns a {@link Page} of entities matching the given condition.
     *
     * @param condition
     * @param pageable
     * @return
     */
    Page<T> findAll(String condition, Pageable pageable , Object... objects);

    /**
     * Returns the number of instances that the given condition will return.
     *
     *
     * @param condition the condition to count instances for
     * @return the number of instances
     */
    long count(String condition, Object... objects);

    /**
     * 获取当前DAO对应的实体模型
     * @return
     */
    Class<T> getEntityClass();

    /**
     * 执行自定义命令
     * @param command
     * @return
     */
    <S> S executeCommand(Command command);


}
