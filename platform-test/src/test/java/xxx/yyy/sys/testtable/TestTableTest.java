package xxx.yyy.sys.testtable;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xxx.yyy.sys.base.BaseTest;
import xxx.yyy.sys.test.model.TestTable;
import xxx.yyy.sys.test.repository.TestTableRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by serv on 14-5-30.
 */
public class TestTableTest extends BaseTest {


    @Autowired
    TestTableRepository testTableRepository;

    @Test
    public void insert(){

        TestTable t = new TestTable();
        t.setIid(0);
        t.setName("ddddf");
        TestTable t1 = new TestTable();
        t1.setIid(1);
        t1.setName("ddddf");

        testTableRepository.save(t);
        testTableRepository.save(t1);
    }

    @Test
    public void list(){
        List<Integer> it = new ArrayList<>();
        it.add(0);
        it.add(1);
        long count = testTableRepository.count("iid in ?1 ",it);
        assertThat(count).isGreaterThan(0);
    }

    @Test
    public void testIn(){
        Iterable<TestTable> all = testTableRepository.findAll("x.iid in (select t.iid from TestTable t )");
        assertThat(all).isNotEmpty();
    }

}
