package xxx.yyy.sys.base.context;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import xxx.yyy.framework.common.enumeration.DepartmentType;
import xxx.yyy.sys.rbac.model.Department;
import xxx.yyy.sys.rbac.model.User;

import java.util.Collection;

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
    public Collection<String> getGroupIds() {
        return Collections2.transform(getGroupList(),new Function<Department, String>() {
            @Override
            public String apply(Department input) {
                return input.getId();
            }
        });
    }


    /**
     * 获取当前用户的群组
     *
     * @return
     */
    public Collection<Department> getGroupList() {
        return getIgnoreTypeList(IGNORE_ORG_DEPARTMENT);
    }

}
