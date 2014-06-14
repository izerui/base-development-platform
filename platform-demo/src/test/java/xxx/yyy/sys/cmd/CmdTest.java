package xxx.yyy.sys.cmd;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xxx.yyy.sys.base.BaseTest;
import xxx.yyy.sys.base.jpa.cmd.Command;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.testtable.service.TestTableService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by serv on 14-6-14.
 */
public class CmdTest extends BaseTest {

    @Autowired
    TestTableService testTableService;


    @Test
    public void testCMD(){
        testTableService.executeCommand(new Command() {
            @Override
            public Object execute(EntityManager entityManager) {
                Query query = entityManager.createQuery("select u from User u");
                List<User> resultList = query.getResultList();
                assertThat(resultList).have(new Condition() {
                    @Override
                    public boolean matches(Object value) {
                        User user = (User) value;
                        return user.getId().equals("admin");
                    }
                });
                log.info("测试数据 ：{}",resultList.toString());
                return null;
            }
        });
    }

}
