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
package xxx.yyy.sys.base.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import xxx.yyy.sys.base.model.BaseModel;
import xxx.yyy.sys.base.jpa.cmd.Command;
import xxx.yyy.sys.datafilter.DataFilterType;

import java.util.List;

/**
 * service 基类接口
 * @param <T> 操作的Entity 类型
 * Created by serv on 2014/6/1.
 */

public interface BaseService<T extends BaseModel>{

    /**
     * 附加单位查询条件 对于 Specification 查询模型方法无效
     * @param orgId
     * @return
     */
    BaseService<T> queryOrgId(String orgId);

    /**
     * 附加未删除标志查询条件 对于 Specification 查询模型方法无效
     * @return
     */
    BaseService<T> queryUnDeleted();

    /**
     * 附加已删除标志查询条件 对于 Specification 查询模型方法无效
     * @return
     */
    BaseService<T> queryDeleted();

    /**
     * 是否过滤数据权限 对于 Specification 查询模型方法无效
     * @return
     */
    BaseService<T> dataFilter(DataFilterType dataFilterType);

    /**
     * 通过条件查找实体
     * @param condition 不带 where的 jpql 条件 例如: name = ?1 and title = ?2
     * @param objects 通配符对应的参数
     *                如果是like 需要自行添加 %
     * @return T or null
     */
    T findOne(String condition, Object... objects);

    /**
     * 查询列表
     * @param condition
     * @param objects
     * @return  非null 的 List<T>
     */
    List<T> findAll(String condition, Object... objects);

    /**
     * 查询列表
     * @param condition
     * @param sort
     * @param objects
     * @return 非null 的 List<T>
     */
    List<T> findAll(String condition, Sort sort, Object... objects);

    /**
     * 查询列表
     * @param condition
     * @param pageable
     * @param objects
     * @return 非null 的 Page<T>
     */
    Page<T> findAll(String condition, Pageable pageable, Object... objects);

    /**
     * 查询count值
     * @param condition
     * @param objects
     * @return
     */
    long count(String condition, Object... objects);

    /**
     * 执行一个自定义命令
     * @param command
     * @param <S>
     * @return Object
     */
    <S> S executeCommand(Command command);

    /**
     * 列出所有
     * @return 非null 的 List<T>
     */
    List<T> findAll();

    /**
     * 列出所有
     * @param sort
     * @return 非null 的 List<T>
     */
    List<T> findAll(Sort sort);

    /**
     * 列出所有
     * @param ids
     * @return
     */
    List<T> findAll(Iterable<String> ids);

    /**
     * 保存多个实体
     * @param entities
     * @return
     */
    List<T> save(Iterable<T> entities);

    /**
     * 真删除 相当于 delete from table where id = ?1 or id = ?2 or ....
     * @param ids
     */
    void deleteInBatch(Iterable<T> ids);

    /**
     * 批量真删除 相当于 delete from table
     * 可以附加 queryUndelete() queryDelete() queryOrgId() 条件
     * 则类似: delete from table where deleteStatus = 0
     * delete from table where orgId = ?1
     */
    void deleteAllInBatch();

    /**
     * 无条件的循环全部删除,不可附加条件
     */
    void deleteAll();

    /**
     * 查询列表
     * @param pageable
     * @return
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 保存一个实体
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * 保存并且持久所有数据更改
     * @param entity
     * @return
     */
    T saveAndFlush(T entity);

    /**
     * 持久所有数据库更改
     */
    void flush();

    /**
     * 查找一个实体,走缓存,找不到对象会返回null
     * @param id
     * @return
     */
    T findOne(String id);

    /**
     * load一个对象,不走缓存,找不到对象会抛异常
     * @param id
     * @return
     */
    T getOne(String id);

    /**
     * 判断是否存在
     * @param id
     * @return
     */
    boolean exists(String id);

    /**
     * 查询count ,可以附加 queryUndelete() queryDelete() queryOrgId() 条件
     * @return
     */
    long count();

    /**
     * 假删除,找不到实体则不删除
     * @param id
     */
    void delete(String id);

    /**
     * 假删除一个实体
     * @param entity
     */
    void delete(T entity);

    /**
     * 假删除,多个实体
     * @param entities
     */
    void delete(Iterable<T> entities);

    /**
     * 假删除,根据ids假删除多个实体
     * @param ids
     */
    void deleteByIds(Iterable<String> ids);

    
}
