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
package xxx.yyy.framework.jpa.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import xxx.yyy.framework.jpa.PlatformJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.jpa.repository.query.QueryUtils.*;

/**
 * Created by serv on 14-5-29.
 */
public class PlatformRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements PlatformJpaRepository<T,ID>{

    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ?> entityInformation;

    public PlatformRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = JpaEntityInformationSupport.getMetadata(domainClass, entityManager);
    }


    @Override
    public T findOne(String condition, Object... objects) {
        return (T) createQuery(condition, null, objects).getSingleResult();
    }

    @Override
    public Iterable<T> findAll(String condition, Object... objects) {
        return createQuery(condition, null, objects).getResultList();
    }

    @Override
    public Iterable<T> findAll(String condition, Sort sort, Object... objects) {
        return createQuery(condition, sort, objects).getResultList();
    }

    @Override
    public Page<T> findAll(String condition, Pageable pageable, Object... objects) {

        if(pageable==null){
            return new PageImpl<T>((List<T>) findAll(condition,objects));
        }

        Long total = count(condition,objects);

        Query query = createQuery(condition, pageable.getSort(), objects);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<T> content = total > pageable.getOffset() ? query.getResultList() : Collections.<T> emptyList();

        return new PageImpl<T>(content, pageable, total);
    }

    @Override
    public long count(String condition, Object... objects) {
        return QueryUtils.executeCountQuery(createCountQuery(condition, objects));
    }

    @Override
    public Class<T> getEntityClass() {
        return entityInformation.getJavaType();
    }

    //别名
    private final static String ALIAS = "x";

    private final static String FIND_ALL_QUERY_STRING = "from %s "+ALIAS;

    private TypedQuery createCountQuery(String condition, Object[] objects){
        TypedQuery query = entityManager.createQuery(getCountQueryString()+" where "+condition,Long.class);
        setParamToQuery(query,objects);
        return query;
    }


    private Query createQuery(String condition, Sort sort, Object[] objects) {

        String  ql  = getQueryString(FIND_ALL_QUERY_STRING, this.entityInformation.getEntityName());

        ql +=  " where "+condition;

        ql = applySorting(ql, sort, ALIAS);

        Query query = this.entityManager.createQuery(ql);

        setParamToQuery(query, objects);
        return query;

    }

    private void setParamToQuery(Query query,Object[] objects){

        if(objects==null){
            return;
        }

        int i = 0;
        for(Object value:objects){
            i++;
            query.setParameter(i,value);
        }
    }


    private String getCountQueryString() {

        String countQuery = String.format(COUNT_QUERY_STRING, ALIAS, "%s");
        return getQueryString(countQuery, entityInformation.getEntityName());
    }


}
