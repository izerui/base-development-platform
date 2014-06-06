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

import org.hibernate.validator.constraints.NotBlank;
import xxx.yyy.framework.common.enumeration.State;
import xxx.yyy.framework.common.utilities.I18nUtils;
import xxx.yyy.framework.common.utilities.PinyinUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Created by serv on 2014/6/1.
 */
@MappedSuperclass
public abstract class BaseInfoModel extends BaseModel {

    //名称
    @Column(name = "NAME", length = 64, nullable = false)
    protected String name;

    //状态
    @Column(name = "STATE", nullable = false, length = 1)
    protected Integer state = State.Enable.getValue();


    /**
     * 拼音
     */
    @Column(name = "SPELL", length = 64)
    private String spell;

    /**
     * 简拼
     */
    @Column(name = "SHORT_SPELL", length = 64)
    private String shortSpell;

    //备注
    @Column(name="REMARK",length=512)
    protected String remark;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.setSpell(PinyinUtils.getPinYin(name));
        this.setShortSpell(PinyinUtils.getPinYinHeadChar(name));
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    // 获取状态名称
    @Transient
    public String getStateName() {
        return I18nUtils.getMessage(State.class, this.state);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getShortSpell() {
        return shortSpell;
    }

    public void setShortSpell(String shortSpell) {
        this.shortSpell = shortSpell;
    }


}
