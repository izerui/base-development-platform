package xxx.yyy.sys.base.context;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.sys.rbac.model.Department;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.rbac.service.AccountService;

import java.util.Collection;
import java.util.List;

/**
 * 群组、单位、部门上下文抽象类
 * Created by serv on 14-6-5.
 */
public abstract class AbstractDeptTriangleContext extends AbstractUserContext{


    private List<Department> departmentList;

    @Override
    protected void init() {
        departmentList = SpringContextHolder.getBean(AccountService.class).getOne(getUser().getId()).getDeptList();
    }


    public AbstractDeptTriangleContext(User user) {
        super(user);
    }


    /**
     * 获取当前用户忽略类型的集合
     * @param ignoreTypes
     * @return
     */
    protected Collection<Department> getIgnoreTypeList(final String ignoreTypes){
        return Collections2.transform(departmentList,new Function<Department, Department>() {
            @Override
            public Department apply(Department input) {
                if (ignoreTypes == null || !ArrayUtils.contains(StringUtils.split(","), input.getType())) {
                    return input;
                }
                return null;
            }
        });
    }


}
