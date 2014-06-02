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
package xxx.yyy.framework.common.enumeration;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by serv on 2014/6/1.
 */
public enum DeleteStatusEnum {
    ALL(null),UNDELETE(false),DELETE(true);

    private Boolean value = null;

    DeleteStatusEnum(Boolean value) {
        this.value = value;
    }

    public Boolean getValue(){
        return value;
    }

    public Specification applyDeleteStatus(Specification spec){

        if(value==null){
            return spec;
        }


        final Boolean b = value;

        Specification deleteSpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.and(cb.equal(root.get("deleteStatus"),b));
            }
        };

        if(spec==null){
            return deleteSpecification;
        }else{
            return Specifications.where(spec).and(deleteSpecification);
        }
    }

}
