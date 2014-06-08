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
package xxx.yyy.sys.base.model;

import xxx.yyy.framework.common.utilities.CollectionUtils;
import xxx.yyy.framework.common.utilities.Treeable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;


/**
 * 分类树状模型基类
 * @author serv
 *
 */
@MappedSuperclass
public abstract class BaseTreeInfoModel extends BaseInfoModel implements Treeable {


    public BaseTreeInfoModel() {
    }

    public BaseTreeInfoModel(String type) {
        this.type = type;
    }


    @Column(name = "TYPE", nullable = false, length = 2)
    protected String type;


    /**
     * 获取父类名称
     */
    @Transient
    public String getParentName() {
        return this.getParent() == null ? "" : getParent().getName();
    }
    /**
     * 获取父类ID
     * @return String
     */
    @Transient
    public String getParentId() {
        return this.getParent() == null ? "" : getParent().getId();
    }

    /**
     * 获取资源类型
     *
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * 设置资源类型
     *
     * @param type
     *            类型
     * @see xxx.yyy.framework.common.enumeration.ResourceType
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 将children list format 成不包含嵌套关系的一个列表
     * @param containsSelf 是否包含自身
     * @param  ignoreType 忽略的类型,多个 以 , 区分
     * @return 一个新的集合
     */
    public List mergerTolist(boolean containsSelf , String ignoreType){
        List list = new ArrayList<>();
        if(containsSelf){
            list.add(this);
        }
        list.addAll(CollectionUtils.mergerChildrenTree(this.getChildren(), ignoreType));

        return list;
    }

    /**
     * 返回格式化后的树形列表
     * @param containsSelf 是否包含自身
     * @param ignoreType 要排除的类型 忽略的类型,多个 以 , 区分
     * @return
     */
    public List formatToTree(boolean containsSelf,String ignoreType){

        List list = new ArrayList<>();
        if(containsSelf){
            list.add(this);
        }
        list.addAll(CollectionUtils.formatToTree(getChildren(),ignoreType));
        return list;

    }



}
