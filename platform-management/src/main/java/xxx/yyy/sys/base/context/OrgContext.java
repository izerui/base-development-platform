package xxx.yyy.sys.base.context;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.framework.common.enumeration.DepartmentType;
import xxx.yyy.framework.common.utilities.CollectionUtils;
import xxx.yyy.sys.rbac.model.Department;
import xxx.yyy.sys.rbac.model.User;
import xxx.yyy.sys.rbac.service.DepartmentService;

import java.util.List;

/**
 * 单位上下文
 * Created by serv on 14-6-5.
 */
public class OrgContext extends AbstractDeptTriangleContext {

    private final static String IGNORE_DEPT_GROUP = DepartmentType.DEPARTMENT.getValue()+","+DepartmentType.GROUP.getValue();

    //用户的最上层单位
    private Department rootOrg;
    //当前单位
    private Department org;

    //包含当前单位和子单位的集合
    private List<Department> childOrgList;

    public OrgContext(User user) {
        super(user);
    }

    @Override
    protected void init() {
        super.init();
        rootOrg = SpringContextHolder.getBean(DepartmentService.class).findRootDept(this.getOrgId());
        if(StringUtils.isNotEmpty(getOrgId())){
            childOrgList = SpringContextHolder.getBean(DepartmentService.class).getOne(getOrgId()).mergerTolist(true,IGNORE_DEPT_GROUP);

        }
        org = (Department) CollectionUtils.find(childOrgList,new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                Department orgDept = (Department) object;
                if(StringUtils.equals(orgDept.getId(), getOrgId())){
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 获取登录用户当前机构子机构id集合
     *
     * @return
     */
    public List<String> getChildOrgIds() {
        return CollectionUtils.extractToList(childOrgList,"id");
    }

    /**
     * 获取用的当前单位和子单位集合
     *
     * @return
     */
    public List<Department> getChildOrgs() {
        return childOrgList;
    }

    /**
     * 获取当前登录人员所在最上级单位
     *
     * @return
     */
    public Department getRootOrg() {
        return rootOrg;
    }

    /**
     * 获取当前登录人员所在顶级单位id
     *
     * @return
     */
    public String getRootOrgId() {
        return rootOrg.getId();
    }

    /**
     * 获取当前登录人员所在单位
     * @return
     */
    public Department getOrg(){

        return org;
    }

    /**
     * 获取当前登录人员所在单位
     * @return
     */
    public String getOrgId(){

        return getUser().getOrgId();
    }




}
