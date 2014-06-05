package xxx.yyy.sys.base.context;

import xxx.yyy.sys.rbac.model.Department;
import xxx.yyy.sys.rbac.model.User;

import java.util.List;

/**
 * 群组上下文
 * Created by serv on 14-6-5.
 */
public class GroupContext extends AbstractDeptTriangleContext {

    public GroupContext(User user) {
        super(user);
    }


    /**
     * 获得当前用户群组id集合
     *
     * @return
     */
    public List<String> getGroupIds() {
        return null;
    }


    /**
     * 获取当前用户的群组
     *
     * @return
     */
    public List<Department> getGroupList() {
        return null;
    }

}
