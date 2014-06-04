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
package xxx.yyy.sys.base.jpa.factory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import xxx.yyy.sys.base.jpa.PlatformJpaRepository;
import xxx.yyy.sys.base.jpa.impl.PlatformRepositoryImpl;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by serv on 14-5-29.
 */
public class PlatformJpaRepositoryFactoryBean<T extends JpaRepository<S,ID>,S,ID extends Serializable> extends JpaRepositoryFactoryBean<T,S,ID> {
    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new PlatformRepositoryFactory(entityManager);
    }


    private static class PlatformRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {

        private EntityManager entityManager;

        public PlatformRepositoryFactory(EntityManager entityManager) {
            super(entityManager);

            this.entityManager = entityManager;
        }

        protected Object getTargetRepository(RepositoryMetadata metadata) {

            return new PlatformRepositoryImpl((Class<T>) metadata.getDomainType(), entityManager);
        }

        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

            // The RepositoryMetadata can be safely ignored, it is used by the JpaRepositoryFactory
            //to check for QueryDslJpaRepository's which is out of scope.
            return PlatformJpaRepository.class;
        }
    }


}
