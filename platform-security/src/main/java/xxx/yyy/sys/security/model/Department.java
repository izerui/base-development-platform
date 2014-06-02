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
package xxx.yyy.sys.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import xxx.yyy.framework.common.model.BaseTreeInfoModel;

import javax.persistence.*;
import java.util.List;

/**
 * 
 * 部门与机构
 * 
 * 
 * @author serv
 *
 */
@Entity
@Table(name="SYS_RBAC_DEPARTMENT")
public class Department extends BaseTreeInfoModel{


    /**
     * 父分类
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
    private Department parent ;


    @OrderBy("sort ASC")
    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY,cascade={CascadeType.ALL})
    private List<Department> children;

	/**
	 * 简称
	 */
    private String shortName ;


	/**
	 * 机构领导 指向岗位
	 */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="LEADER_ID",nullable=true)
	private Post leader ;
	
	/**
	 * 上级领导 （分管领导）
	 */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="SUPER_LEADER_ID",nullable=true)
	private User superLeader ;


	

	/**
	 * 编号、机构代码
	 */
    @Column(name="CODE",length=32)
	private String code ;

    /**
     * 构造方法
     * @param type
     */
    protected Department(String type) {
        super(type);
    }


    /**
	 * @return the leader
	 */
	@JsonIgnore
	public Post getLeader() {
		return leader;
	}

	/**
	 * @param leader the {@link xxx.yyy.sys.security.model.Post} to set
	 */
	public void setLeader(Post leader) {
		this.leader = leader;
	}

	/**
	 * @return the {@link xxx.yyy.framework.common.enumeration.DepartmentType}
	 */
	@JsonIgnore
	public User getSuperLeader() {
		return superLeader;
	}

	/**
	 * @param superLeader the {@link xxx.yyy.sys.security.model.User} to set
	 */
	public void setSuperLeader(User superLeader) {
		this.superLeader = superLeader;
	}



	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    @JsonIgnore
    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    @JsonIgnore
    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
    }
}
