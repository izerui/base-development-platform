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

import com.fasterxml.uuid.Generators;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * UUID主键父类
 * 
 * @author serv
 *
 */
@MappedSuperclass
public abstract class IdEntity implements Idable,Serializable{
	
	private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ID",length = 46, nullable = false,unique=true)
	protected String id = new IDGenerator(){
        @Override
        public String generateID() {
            UUID uuid = Generators.randomBasedGenerator().generate();
            return uuid.toString();
        }
    }.generateID();

    /**
     * 主键生成接口
     * Created by serv on 14-5-29.
     */
    protected static interface IDGenerator {
        String generateID();
    }


	public String getId() {
		if (isEmpty(id)) {
			return null;
		}
		return this.id;
	}

	public void setId(String id) {
		if (isEmpty(id)) {
			this.id = null;
		} else {
			this.id = id;
		}
		
	}

}
