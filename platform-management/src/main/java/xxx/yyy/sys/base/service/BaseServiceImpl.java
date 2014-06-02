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
import xxx.yyy.framework.common.PlatformException;
import xxx.yyy.framework.common.model.BaseModel;
import xxx.yyy.framework.jpa.PlatformJpaRepository;
import xxx.yyy.framework.jpa.cmd.Command;

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

    private transient PlatformJpaRepository<T,String> repository;

    //获取Model 对应的 repository bean
    private PlatformJpaRepository<T,String> getRepository(){

        if(repository==null){
            // 获取父类声明的泛型类
            Type type = getClass().getGenericSuperclass();
            Type[] trueType = ((ParameterizedType) type).getActualTypeArguments();
            Class<T> currentEntityClass =  (Class<T>) trueType[0];

            //找到所有的Repository bean
            String[] beanNamesForType = applicationContext.getBeanNamesForType(PlatformJpaRepository.class);
            for (String beanName : beanNamesForType){
                PlatformJpaRepository jpaRepository = (PlatformJpaRepository) applicationContext.getBean(beanName);

                //如果当前repository的操作model类 跟 当前泛型类一致 , 则将当前respository 作为当前service的操作主repository
                if(jpaRepository.getEntityClass().equals(currentEntityClass)){
                    repository = jpaRepository;
                    break;
                }
            }

        }

        if(repository==null){
            throw new PlatformException(getClass().getName()+" 未声明泛型Model,或者未找到对应的repository 操作bean");
        }

        return repository;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public BaseService queryOrgId(String orgId) {
        getRepository().queryOrgId(orgId);
        return this;
    }

    @Override
    public BaseService queryUnDeleted() {
        getRepository().queryUnDeleted();
        return this;
    }

    @Override
    public BaseService queryDeleted() {
        getRepository().queryDeleted();
        return this;
    }

    @Override
    public T findOne(String condition, Object... objects) {
        return getRepository().findOne(condition,objects);
    }

    @Override
    public List<T> findAll(String condition, Object... objects) {
        return getRepository().findAll(condition,objects);
    }

    @Override
    public List<T> findAll(String condition, Sort sort, Object... objects) {
        return getRepository().findAll(condition,sort,objects);
    }

    @Override
    public Page<T> findAll(String condition, Pageable pageable, Object... objects) {
        return getRepository().findAll(condition,pageable,objects);
    }

    @Override
    public long count(String condition, Object... objects) {
        return getRepository().count(condition,objects);
    }

    @Override
    @Transactional(readOnly = false)
    public <S> S executeCommand(Command command) {
        return getRepository().executeCommand(command);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    @Override
    public List<T> findAll(Iterable<String> ids) {
        return getRepository().findAll(ids);
    }

    @Override
    @Transactional(readOnly = false)
    public List<T> save(Iterable<T> entities) {
        return getRepository().save(entities);
    }

    @Override
    public T saveAndFlush(T entity) {
        return getRepository().saveAndFlush(entity);
    }

    @Override
    public void flush() {
        getRepository().flush();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteInBatch(Iterable<T> entities) {
        getRepository().deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }

    @Override
    public void deleteAll() {
        getRepository().deleteAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    @Transactional(readOnly = false)
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T findOne(String id) {
        return getRepository().findOne(id);
    }

    @Override
    public T getOne(String id) {
        return getRepository().getOne(id);
    }

    @Override
    public boolean exists(String id) {
        return getRepository().exists(id);
    }

    @Override
    public long count() {
        return getRepository().count();
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
        List<T> entities = getRepository().findAll(ids);
        delete(entities);
    }
}
