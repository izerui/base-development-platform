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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import xxx.yyy.framework.jpa.PlatformJpaRepository;
import xxx.yyy.framework.jpa.cmd.Command;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.jpa.repository.query.QueryUtils.*;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * Created by serv on 14-5-29.
 */
public class PlatformRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements PlatformJpaRepository<T,ID>{

    //默认忽略删除状态
    private Boolean deleteStatus = null;
    //过滤的操作类型 CREATE READ PRINGT DELETE UPDATE
    private String operationType = null;
    private String orgId = null;

    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ?> entityInformation;

    public PlatformRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = JpaEntityInformationSupport.getMetadata(domainClass, entityManager);
    }


    @Override
    public PlatformJpaRepository<T, ID> queryOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    @Override
    public PlatformJpaRepository queryUnDeleted() {
        deleteStatus = false;
        return this;
    }

    @Override
    public PlatformJpaRepository queryDeleted() {
        deleteStatus = true;
        return this;
    }

    @Override
    public PlatformJpaRepository<T, ID> dataFilter(String operationType) {
        this.operationType = operationType;
        return this;
    }

    @Override
    public T findOne(String condition, Object... objects) {
        return (T) createQuery(condition, null, objects).getSingleResult();
    }

    @Override
    public List<T> findAll(String condition, Object... objects) {
        return createQuery(condition, null, objects).getResultList();
    }

    @Override
    public List<T> findAll(String condition, Sort sort, Object... objects) {
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
    public long count() {
        return QueryUtils.executeCountQuery(createCountQuery(null, null));
    }

    @Override
    public long count(String condition, Object... objects) {
        return QueryUtils.executeCountQuery(createCountQuery(condition, objects));
    }

    @Override
    public Class<T> getEntityClass() {
        return entityInformation.getJavaType();
    }

    @Override
    public <S> S executeCommand(Command command) {
        return (S) command.execute(entityManager);
    }


    @Override
    protected TypedQuery<Long> getCountQuery(Specification<T> spec) {
        ConditionApplier applier = new ConditionApplier(spec);
        return super.getCountQuery(applier.applySpecification());
    }

    @Override
    protected TypedQuery<T> getQuery(Specification<T> spec, Pageable pageable) {
        ConditionApplier applier = new ConditionApplier(spec);
        return super.getQuery(applier.applySpecification(), pageable);
    }

    @Override
    protected TypedQuery<T> getQuery(Specification<T> spec, Sort sort) {
        ConditionApplier applier = new ConditionApplier(spec);
        return super.getQuery(applier.applySpecification(), sort);
    }


    //别名
    private final static String ALIAS = "x";

    private final static String FIND_ALL_QUERY_STRING = "from %s "+ALIAS;

    private TypedQuery createCountQuery(String condition, Object[] objects){

        ConditionApplier applier = new ConditionApplier(condition,objects);

        TypedQuery query = entityManager.createQuery(getCountQueryString()+" where "+applier.applyCondition(),Long.class);

        applier.applyQueryParameter(query);

        return query;
    }


    private Query createQuery(String condition, Sort sort, Object[] objects) {

        String  ql  = getQueryString(FIND_ALL_QUERY_STRING, this.entityInformation.getEntityName());

        ConditionApplier applier = new ConditionApplier(condition,objects);

        ql +=  " where "+applier.applyCondition();
        ql = applySorting(ql, sort, ALIAS);

        Query query = this.entityManager.createQuery(ql);

        applier.applyQueryParameter(query);

        return query;

    }


    private class ConditionApplier{


        private StringBuilder condition;
        private Specification specification;
        private Object[] objects;

        ConditionApplier(String condition , Object[] objects) {
            this.condition = new StringBuilder(condition==null ? "" : condition);
            this.objects = objects;
        }

        ConditionApplier(Specification specification) {
            this.specification = specification;
        }

        String applyCondition(){
            //添加删除状态条件
            if(deleteStatus!=null){
                if(!isEmpty(condition.toString())){
                    condition.append(" and ");
                }
                condition.append(ALIAS+".deleteStatus = :deleteStatus");
            }
            //添加机构条件
            if(!isEmpty(orgId)){
                if(!isEmpty(condition)){
                    condition.append(" and ");
                }
                condition.append(ALIAS+".orgId = :orgId");
            }
            return condition.toString();
        }

        void applyQueryParameter(Query query){

            if(objects!=null){
                int i = 0;
                for(Object value:objects){
                    i++;
                    query.setParameter(i,value);
                }
            }

            if(deleteStatus!=null){
                query.setParameter("deleteStatus", deleteStatus);
            }
            if(!isEmpty(orgId)){
                query.setParameter("orgId",orgId);
            }
            reset();
        }


        Specification applySpecification(){
            //添加删除状态条件
            if(deleteStatus!=null){

                final Boolean b = deleteStatus;
                specification = Specifications.where(specification).and(new Specification() {
                    @Override
                    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                        return cb.and(cb.equal(root.get("deleteStatus"),b));
                    }
                });
            }

            //添加机构条件
            if(orgId!=null){
                specification = Specifications.where(specification).and(new Specification<T>() {
                    @Override
                    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                        return cb.and(cb.equal(root.get("orgId"),orgId));
                    }
                });
            }
            reset();
            return specification;
        }


        void reset(){
            orgId = null;
            deleteStatus = null;
        }


    }


    private String getCountQueryString() {

        String countQuery = String.format(COUNT_QUERY_STRING, ALIAS, "%s");
        return getQueryString(countQuery, entityInformation.getEntityName());
    }


}
