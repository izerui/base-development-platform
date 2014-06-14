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
package xxx.yyy.sys.datafilter.model;

import xxx.yyy.sys.base.model.BaseInfoModel;
import xxx.yyy.sys.datafilter.FilterConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
* 规则权限
* @author serv
*
*/
@Entity
@Table(name="SYS_FILTER_RULE")
public class FilterRule extends BaseInfoModel {

	/**
	 * 文档类型 对应实体的全类名 例如： xxx.yyy.sys.datafilter.model.FilterRule
	 * 文档Class类型
	 *
	 */
    @Column(name="MODEL_CLASS_NAME",length=128)
	protected String modelClassName ;



	/**
	 * 对应规则的引用id，可为角色，部门ID，人员ID，岗位ID
	 */
    @Column(name="RELATION_ID",length=64)
	protected String relationId  = FilterConstants.RELATION_ID_ANYONE;


	/**
	 * 关联类型,区分是角色权限 还是 用户权限 还是部门权限 或者是岗位权限
     * user: {@link xxx.yyy.sys.rbac.model.User}
     * role: {@link xxx.yyy.sys.rbac.model.Role}
     * department: {@link xxx.yyy.sys.rbac.model.Department}
     * post: {@link xxx.yyy.sys.rbac.model.Post}
	 */
    @Column(name="RELATION_TYPE",length=64)
	protected String relationType ;


	/**
	 * 操作类型 CREATE READ PRINGT DELETE UPDATE
	 */
    @Column(name="OPERATION_TYPE",length=6)
	protected String  operationType ;


	/**
	 * JPQL
	 */
    @Column(name="CONDITION_JPQL",length=540)
	protected String conditionJpql ;


    public String getModelClassName() {
        return modelClassName;
    }

    public void setModelClassName(String modelClassName) {
        this.modelClassName = modelClassName;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getConditionJpql() {
        return conditionJpql;
    }

    public void setConditionJpql(String conditionJpql) {
        this.conditionJpql = conditionJpql;
    }
}
