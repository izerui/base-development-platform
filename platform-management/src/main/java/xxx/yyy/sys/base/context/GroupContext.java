package xxx.yyy.sys.base.context;

import xxx.yyy.framework.common.enumeration.DepartmentType;
import xxx.yyy.framework.common.utilities.CollectionUtils;
import xxx.yyy.sys.rbac.model.Department;
import xxx.yyy.sys.rbac.model.User;

import java.util.List;

/**
 * 群组上下文
 * Created by serv on 14-6-5.
 */
public class GroupContext extends AbstractDeptTriangleContext {

    private final static String IGNORE_ORG_DEPARTMENT = DepartmentType.ORGANIZATION.getValue()+","+DepartmentType.DEPARTMENT.getValue();

    public GroupContext(User user) {
        super(user);
    }


    /**
     * 获得当前用户群组id集合
     *
     * @return
     */
    public List<String> getGroupIds() {
        return CollectionUtils.extractToList(getGroupList(),"id");
    }


    /**
     * 获取当前用户的群组
     *
     * @return
     */
    public List<Department> getGroupList() {
        return getIgnoreTypeList(IGNORE_ORG_DEPARTMENT);
    }

}
