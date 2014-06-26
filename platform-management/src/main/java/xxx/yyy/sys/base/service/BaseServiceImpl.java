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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import xxx.yyy.sys.base.jpa.PlatformJpaRepository;
import xxx.yyy.sys.base.jpa.cmd.Command;
import xxx.yyy.sys.base.model.BaseModel;
import xxx.yyy.sys.datafilter.DataFilterType;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 抽象基类
 * Created by serv on 2014/6/1.
 */
@Transactional(readOnly = true)
public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T>,ApplicationContextAware{

    protected ApplicationContext applicationContext;

    private transient PlatformJpaRepository<T> repository;

    @PostConstruct
    public void initRepository() {
        // 获取父类声明的泛型类
        Type type = getClass().getGenericSuperclass();
        Type[] trueType = ((ParameterizedType) type).getActualTypeArguments();
        Class<T> currentEntityClass =  (Class<T>) trueType[0];

        //找到所有的Repository bean
        String[] beanNamesForType = applicationContext.getBeanNamesForType(PlatformJpaRepository.class);
        for (String beanName : beanNamesForType){
            PlatformJpaRepository jpaRepository = (PlatformJpaRepository) applicationContext.getBean(beanName);

            //如果当前repository的操作model类 跟 当前泛型类一致 , 则将当前respository 作为当前service的操作主repository
            if(currentEntityClass.equals(jpaRepository.getEntityClass())){
                repository = jpaRepository;
                break;
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public BaseService<T> queryOrgId(String orgId) {
        repository.queryOrgId(orgId);
        return this;
    }

    @Override
    public BaseService<T> queryOrgId() {
        repository.queryOrgId();
        return this;
    }

    @Override
    public BaseService<T> queryUnDeleted() {
        repository.queryUnDeleted();
        return this;
    }

    @Override
    public BaseService<T> dataFilter(DataFilterType dataFilterType) {
        repository.dataFilter(dataFilterType.getValue());
        return this;
    }

    @Override
    public BaseService<T> queryDeleted() {
        repository.queryDeleted();
        return this;
    }

    @Override
    public T findOne(String condition, Object... objects) {
        return repository.findOne(condition,objects);
    }

    @Override
    public List<T> findAll(String condition, Object... objects) {
        return repository.findAll(condition,objects);
    }

    @Override
    public List<T> findAll(String condition, Sort sort, Object... objects) {
        return repository.findAll(condition,sort,objects);
    }

    @Override
    public Page<T> findAll(String condition, Pageable pageable, Object... objects) {
        return repository.findAll(condition,pageable,objects);
    }

    @Override
    public long count(String condition, Object... objects) {
        return repository.count(condition,objects);
    }

    @Override
    @Transactional(readOnly = false)
    public <S> S executeCommand(Command command) {
        return repository.executeCommand(command);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public List<T> findAll(Iterable<String> ids) {
        return repository.findAll(ids);
    }

    @Override
    @Transactional(readOnly = false)
    public List<T> save(Iterable<T> entities) {
        return repository.save(entities);
    }

    @Override
    public T saveAndFlush(T entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteInBatch(Iterable<T> entities) {
        repository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = false)
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T findOne(String id) {
        return repository.findOne(id);
    }

    @Override
    public T getOne(String id) {
        return repository.getOne(id);
    }

    @Override
    public boolean exists(String id) {
        return repository.exists(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(String id) {
        T t = findOne(id);
        if(null!=t){
            t.setDeleteStatus(true);
            save(t);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(T entity) {
        entity.setDeleteStatus(true);
        save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Iterable<T> entities) {
        for(T t:entities){
            t.setDeleteStatus(true);
        }
        save(entities);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteByIds(Iterable<String> ids) {
        List<T> entities = repository.findAll(ids);
        delete(entities);
    }
}
