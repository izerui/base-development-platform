package xxx.yyy.sys.base.context;

import com.google.common.collect.Lists;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.framework.common.utilities.CollectionUtils;
import xxx.yyy.sys.rbac.model.Department;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.rbac.service.AccountService;

import java.util.List;

/**
 * 群组、单位、部门上下文抽象类
 * Created by serv on 14-6-5.
 */
public abstract class AbstractDeptTriangleContext extends AbstractUserContext{


    private List<Department> departmentList = Lists.newArrayList();

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
    protected List<Department> getIgnoreTypeList(final String ignoreTypes){

        return (List<Department>) CollectionUtils.collect(departmentList, new Transformer() {
            @Override
            public Object transform(Object o) {
                Department t = (Department) o;
                if (ignoreTypes == null || !ArrayUtils.contains(StringUtils.split(","), t.getType())) {
                    return t;
                }
                return null;
            }
        });
    }


}
