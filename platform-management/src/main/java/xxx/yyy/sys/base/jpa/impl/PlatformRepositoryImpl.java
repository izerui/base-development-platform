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
package xxx.yyy.sys.base.jpa.impl;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import xxx.yyy.sys.base.context.SystemContextHolder;
import xxx.yyy.sys.base.jpa.PlatformJpaRepository;
import xxx.yyy.sys.base.jpa.cmd.Command;
import xxx.yyy.sys.datafilter.DataFilterType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.*;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;

/**
 * Created by serv on 14-5-29.
 */
public class PlatformRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements PlatformJpaRepository<T,ID>{

    //默认忽略删除状态
    private Boolean deleteStatus = null;
    //过滤的操作类型 CREATE READ PRINGT DELETE UPDATE
    private String dataFilterType = null;
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
    public PlatformJpaRepository<T, ID> dataFilter(String dataFilterType) {
        this.dataFilterType = dataFilterType;
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
    public Page<T> findAll(Pageable pageable) {
        return this.findAll("", pageable);
    }

    @Override
    public List<T> findAll(Iterable<ID> ids) {
        return this.findAll("x.id in ?1",ids);
    }

    @Override
    public T findOne(ID id) {
        return this.findOne("id = ?1",id);
    }

    @Override
    public List<T> findAll(Sort sort) {
        return createQuery(null,sort,null).getResultList();
    }

    @Override
    public List<T> findAll() {
        return createQuery(null,null,null).getResultList();
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


//    @Override
//    protected TypedQuery<Long> getCountQuery(Specification<T> spec) {
//        ConditionApplier applier = new ConditionApplier(spec);
//        return super.getCountQuery(applier.applySpecification());
//    }
//
//    @Override
//    protected TypedQuery<T> getQuery(Specification<T> spec, Pageable pageable) {
//        ConditionApplier applier = new ConditionApplier(spec);
//        return super.getQuery(applier.applySpecification(), pageable);
//    }
//
//    @Override
//    protected TypedQuery<T> getQuery(Specification<T> spec, Sort sort) {
//        ConditionApplier applier = new ConditionApplier(spec);
//        return super.getQuery(applier.applySpecification(), sort);
//    }


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

        // select x from table
        String  ql  = QueryUtils.getQueryString(FIND_ALL_QUERY_STRING, this.entityInformation.getEntityName());

        ConditionApplier applier = new ConditionApplier(condition,objects);

        //where
        String conditionQL = applier.applyCondition();
        ql +=  isEmpty(conditionQL)?"":" where "+conditionQL;

        //order by
        ql = QueryUtils.applySorting(ql, sort, ALIAS);

        Query query = this.entityManager.createQuery(ql);

        //parameters
        applier.applyQueryParameter(query);

        return query;

    }


    private class ConditionApplier{


        private String condition = null;
        private Specification specification;
        private Object[] objects;

        ConditionApplier(String condition , Object[] objects) {
            if(StringUtils.isNotBlank(condition)&&startsWithIgnoreCase(condition,"order")){
                this.condition = "1=1 "+condition;
            }
            if(StringUtils.isNotBlank(condition)){
                this.condition = condition;
            }
            this.objects = objects;
        }

        String applyCondition(){
            List<String> conditions = Lists.newArrayList();
            //添加删除状态条件
            if(deleteStatus!=null)  {
                conditions.add(ALIAS+".deleteStatus = :deleteStatus");
            }
            //添加机构条件
            if(!isEmpty(orgId )) {
                conditions.add(ALIAS+".orgId = :orgId");
            }

            if(StringUtils.equals(dataFilterType, DataFilterType.READ.getValue())){
                List<String> jpqls = SystemContextHolder.getSessionContext().getFilterRuleJpqlList(
                        getEntityClass(), dataFilterType);
                conditions.addAll(jpqls);

            }

            if(condition!=null) {
                conditions.add(condition);
            }
            condition = StringUtils.join(conditions, " and ");
            return condition;
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

            if(!isEmpty(dataFilterType)){
                //添加数据过滤变量信息
                Map<String, Object> filterParameters = null;
                filterParameters = SystemContextHolder.getSessionContext().getFilterParameters();
                if(filterParameters!=null){
                    Iterator<String> iterator = filterParameters.keySet().iterator();
                    while(iterator.hasNext()){
                        String key = iterator.next();
                        if(StringUtils.contains(condition,":"+key)){
                            query.setParameter(key,filterParameters.get(key));
                        }
                    }
                }
            }

            reset();
        }


        void reset(){
            orgId = null;
            deleteStatus = null;
            dataFilterType = null;
        }


    }


    private String getCountQueryString() {

        String countQuery = String.format(QueryUtils.COUNT_QUERY_STRING, ALIAS, "%s");
        return QueryUtils.getQueryString(countQuery, entityInformation.getEntityName());
    }


}
