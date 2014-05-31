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
package xxx.yyy.framework.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 分类树状模型基类
 * @author serv
 *
 */
@MappedSuperclass
public abstract  class CategoryTreeModel extends BaseModel{
  
	//分类名称
    @Column(name="NAME",length=32,nullable=false)
	protected String name ;
	
	/**
	 * 父分类
	 */
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
	protected CategoryTreeModel parent ;
	
	
	/**
	 * 当前分类是否为父类型
	 */
    @Column(name="IS_PARENT")
	protected  Boolean isParent = false;
	
	

	/**
	 * 子分类集合
	 */
    @JsonIgnore
    @OrderBy("sort ASC")
    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY,cascade={CascadeType.ALL})
	protected List<? extends CategoryTreeModel> children = new ArrayList();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryTreeModel getParent() {
        return parent;
    }

    public void setParent(CategoryTreeModel parent) {
        this.parent = parent;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public List<? extends CategoryTreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<? extends CategoryTreeModel> children) {
        this.children = children;
    }
}
