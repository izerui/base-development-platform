package xxx.yyy.sys.test.model;

import xxx.yyy.framework.common.model.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by serv on 14-5-30.
 */
@Entity
@Table(name = "TEST_TABLE")
public class TestTable extends IdEntity{
    @Column(name = "iid",nullable = false)
    private int iid;
    @Column(name="name",length = 64,nullable = true)
    private String name;

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
