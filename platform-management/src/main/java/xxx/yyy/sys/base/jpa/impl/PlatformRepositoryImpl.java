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

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;
import xxx.yyy.framework.common.PlatformException;
import xxx.yyy.sys.base.context.SessionVariable;
import xxx.yyy.sys.base.context.SystemContextHolder;
import xxx.yyy.sys.base.jpa.PlatformJpaRepository;
import xxx.yyy.sys.base.jpa.cmd.Command;
import xxx.yyy.sys.base.model.Idable;
import xxx.yyy.sys.datafilter.context.FilterContext;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * Created by serv on 14-5-29.
 */
public class PlatformRepositoryImpl<T extends Idable> extends SimpleJpaRepository<T,String> implements PlatformJpaRepository<T>{

    //默认忽略删除状态
    private ThreadLocal<Boolean> deleteStatusThread = new ThreadLocal<Boolean>();
    //过滤的操作类型 CREATE READ PRINGT DELETE UPDATE
    private ThreadLocal<String> dataFilterTypeThread = new ThreadLocal<String>();
    private ThreadLocal<String> orgIdThread = new ThreadLocal<String>();

    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ?> entityInformation;

    public PlatformRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = JpaEntityInformationSupport.getMetadata(domainClass, entityManager);
    }


    public void clearParamAppended(){
        deleteStatusThread.set(null);
        dataFilterTypeThread.set(null);
        orgIdThread.set(null);
    }


    @Override
    public PlatformJpaRepository<T> queryOrgId(String orgId) {
        this.orgIdThread.set(orgId);
        return this;
    }

    @Override
    public PlatformJpaRepository<T> queryOrgId() {
        this.orgIdThread.set(SystemContextHolder.getSessionContext().getOrgContext().getOrgId());
        return this;
    }

    @Override
    public PlatformJpaRepository queryUnDeleted() {
        deleteStatusThread.set(false);
        return this;
    }

    @Override
    public PlatformJpaRepository queryDeleted() {
        deleteStatusThread.set(true);
        return this;
    }

    @Override
    public PlatformJpaRepository<T> dataFilter(String dataFilterType) {
        this.dataFilterTypeThread.set(dataFilterType);
        return this;
    }

    @Override
    @ClearParamAfterMethod
    public T findOne(String condition, Object... values) {
        if(isEmpty(condition)){
            throw new PlatformException("条件不能为空!");
        }
        return (T) createQuery(condition, values).getSingleResult();
    }

    @Override
    @ClearParamAfterMethod
    public List<T> findAll(String condition, Object... objects) {
        return createQuery(condition, objects).getResultList();
    }

    @Override
    @ClearParamAfterMethod
    public List<T> findAll(String condition, Sort sort, Object... objects) {
        return createQuery(condition, sort, objects).getResultList();
    }

    @Override
    @ClearParamAfterMethod
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
    @ClearParamAfterMethod
    public Page<T> findAll(Pageable pageable) {
        return this.findAll("", pageable);
    }

    @Override
    @ClearParamAfterMethod
    public List<T> findAll(Iterable<String> ids) {
        return this.findAll("x.id in ?1",ids);
    }

    @Override
    @ClearParamAfterMethod
    public T findOne(String id) {
        return this.findOne("x.id = ?1", id);
    }

    @Override
    @ClearParamAfterMethod
    public List<T> findAll(Sort sort) {
        return createQuery(null,sort,null).getResultList();
    }

    @Override
    @ClearParamAfterMethod
    public List<T> findAll() {
        return createQuery(null,null).getResultList();
    }


    @Override
    @ClearParamAfterMethod
    public long count() {
        return QueryUtils.executeCountQuery(createCountQuery(null, null));
    }

    @Override
    @ClearParamAfterMethod
    public long count(String condition, Object... objects) {
        return QueryUtils.executeCountQuery(createCountQuery(condition, objects));
    }


    @Override
    @ClearParamAfterMethod
    public void deleteByIds(Iterable<String> ids) {
        List<T> tlist = super.findAll(ids);
        doFilter(tlist);
        super.deleteInBatch(tlist);
    }

    @Override
    @ClearParamAfterMethod
    public void delete(String id) {
        Assert.notNull(id, "给定的ID不能为空!");

        T entity = super.findOne(id);

        delete(entity);
    }

    @Override
    @ClearParamAfterMethod
    public void deleteAll() {
        if(dataFilterTypeThread.get()!=null&&super.count()!=count()){
            throw new PlatformException("包含没有权限删除的数据!");
        }
        super.deleteAll();
    }


    @Override
    @ClearParamAfterMethod
    public T getOne(String s) {
        T one = super.getOne(s);
        doFilter(one);
        return one;
    }

    @Override
    @ClearParamAfterMethod
    public void deleteAllInBatch() {
        if(dataFilterTypeThread.get()!=null&&super.count()!=count()){
            throw new PlatformException("包含没有权限删除的数据!");
        }
        super.deleteAllInBatch();
    }

    @Override
    @ClearParamAfterMethod
    public void delete(Iterable<? extends T> entities) {

        ArrayList<T> entityList = Lists.newArrayList(entities);

        doFilter(entityList);
        super.delete(entities);
    }

    @Override
    @ClearParamAfterMethod
    public void delete(T entity) {
        doFilter(Lists.newArrayList(entity));
        super.delete(entity);
    }

    @Override
    @ClearParamAfterMethod
    public void deleteInBatch(Iterable<T> entities) {
        ArrayList<T> entityList = Lists.newArrayList(entities);
        doFilter(entityList);
        super.deleteInBatch(entities);
    }

    @Override
    @ClearParamAfterMethod
    public <S extends T> List<S> save(Iterable<S> entities) {
        List<S> result = new ArrayList<S>();

        if (entities == null) {
            return result;
        }

        for (S entity : entities) {
            result.add(super.save(entity));
        }

        doFilter(result);

        return result;
    }

    @Override
    @ClearParamAfterMethod
    public <S extends T> S save(S entity) {
        S s = super.save(entity);

        doFilter(entity);
        return s;
    }

    @Override
    @ClearParamAfterMethod
    public <S extends T> S saveAndFlush(S entity) {
        S s = super.saveAndFlush(entity);
        doFilter(entity);
        return s;
    }

    private <S extends T> void doFilter(Collection<S> entityList){

        if(null==entityList){
            return;
        }

        if(null!= dataFilterTypeThread.get()){
            Collection<String> ids = Collections2.transform(entityList,new Function<S, String>() {
                @Override
                public String apply(S input) {
                    return input.getId();
                }
            });
            if(entityList.size()!=count("id in ?1",ids)){
                throw new PlatformException("没有权限!");
            }
        }
    }

    private <S extends T> void doFilter(S entity){

        if(null==entity){
            return;
        }

        if(null!= dataFilterTypeThread.get()){
            if (count("id = ?1",entity.getId())==0) {
                throw new PlatformException("没有权限!");
            }
        }
    }


    @Override
    public Class<T> getEntityClass() {
        return entityInformation.getJavaType();
    }

    @Override
    @ClearParamAfterMethod
    public <S> S executeCommand(Command command) {
        return (S) command.execute(entityManager);
    }


    private TypedQuery createCountQuery(String condition, Object[] objects){

        JpqlQueryHolder queryHolder = new JpqlQueryHolder(condition,objects);

        return queryHolder.createCountQuery();
    }


    /**
     * 声明entityClass的查询
     */
    private Query createQuery(String condition, Sort sort, Object[] objects) {

        JpqlQueryHolder queryHolder = new JpqlQueryHolder(condition,sort,objects);

        return queryHolder.createQuery();
    }

    /**
     * 声明entityClass的查询
     */
    private Query createQuery(String condition, Object[] objects) {
       return createQuery(condition,null,objects);
    }


    private class JpqlQueryHolder {

        //别名
        private final String ALIAS = "x";

        //QUERY ALL
        private final String FIND_ALL_QUERY_STRING = "from %s "+ALIAS;

        //传入的condition 排除列表
        private final String[] IGNORE_CONSTAINS_CHARSEQUENCE = {"where","WHERE","from","FROM"};

        private String condition = null;
        private Sort sort;
        private Object[] objects;


        private JpqlQueryHolder(String condition, Sort sort, Object[] objects) {
            this(condition,objects);
            this.sort = sort;
        }

        private JpqlQueryHolder(String condition, Object[] objects) {

            if(startsWithAny(condition,IGNORE_CONSTAINS_CHARSEQUENCE)){
                throw new PlatformException("查询条件中只能包含WHERE条件表达式!");
            }
            this.condition = trimToNull(condition);
            this.objects = objects;
        }

        private Query createQuery(){
            StringBuilder sb = new StringBuilder();
            // select x from table
            sb.append(QueryUtils.getQueryString(FIND_ALL_QUERY_STRING, entityInformation.getEntityName()))
                //where
                .append(applyCondition());

            Query query = entityManager.createQuery(QueryUtils.applySorting(sb.toString(), sort, ALIAS));
            applyQueryParameter(query);
            return query;
        }

        private TypedQuery<Long> createCountQuery(){
            String ql = String.format(QueryUtils.COUNT_QUERY_STRING, ALIAS, "%s");
            ql = QueryUtils.getQueryString(ql, entityInformation.getEntityName());
            ql += applyCondition();

            TypedQuery<Long> query = entityManager.createQuery(ql,Long.class);
            applyQueryParameter(query);
            return query;
        }

        private String applyCondition(){
            List<String> conditions = Lists.newArrayList();
            //添加删除状态条件
            if(deleteStatusThread.get()!=null)  {
                conditions.add(ALIAS+".deleteStatus = :deleteStatus");
            }
            //添加机构条件
            if(!isEmpty(orgIdThread.get() )) {
                conditions.add(ALIAS+".orgId = :orgId");
            }

            //数据规则ql 以 or 连接
            if(null!= dataFilterTypeThread.get()){
                String filterRuleCondition = "";
                SessionVariable context = SystemContextHolder.getSessionContext();
                if(context!=null){
                    filterRuleCondition = join(context.getFilterRuleJpqlList(getEntityClass(), dataFilterTypeThread.get() , context.getOrgContext().getOrgId())," or ");
                }

                //如果 规则条件返回 null 或者 "" 则 组装 为 (1=1) 否则 (fs=?1 or fs=?2 or fs=?3)
                conditions.add("("+(filterRuleCondition.equals("")?"1=1":filterRuleCondition)+")");
            }

            if(condition!=null) {
                conditions.add(condition);
            }
            condition = join(conditions, " and ");
            return isEmpty(condition)?"":" where "+condition;
        }

        private void applyQueryParameter(Query query){

            if(objects!=null){
                int i = 0;
                for(Object value:objects){
                    i++;
                    query.setParameter(i,value);
                }
            }

            if(deleteStatusThread.get()!=null){
                query.setParameter("deleteStatus", deleteStatusThread.get());
            }
            if(!isEmpty(orgIdThread.get())){
                query.setParameter("orgId", orgIdThread.get());
            }

            if(!isEmpty(dataFilterTypeThread.get())){
                //添加数据过滤变量信息
                FilterContext context = SystemContextHolder.getSessionContext();
                if(context==null){
                    throw new PlatformException("无法获取到当前用户的上下文信息,请验证登录用户状态!");
                }
                Map<String, Object> filterParameters = context.getFilterParameters();
                Iterator<String> iterator = filterParameters.keySet().iterator();
                while(iterator.hasNext()){
                    String key = iterator.next();
                    if(contains(condition,":"+key)){
                        query.setParameter(key,filterParameters.get(key));
                    }
                }

            }

        }



    }

}
