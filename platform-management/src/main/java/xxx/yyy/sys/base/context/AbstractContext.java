package xxx.yyy.sys.base.context;

import com.google.common.collect.Lists;
import xxx.yyy.framework.common.application.SpringContextHolder;
import xxx.yyy.framework.common.enumeration.DepartmentType;
import xxx.yyy.framework.common.utilities.CollectionUtils;
import xxx.yyy.sys.rbac.model.Department;
import xxx.yyy.sys.rbac.service.AccountService;
import xxx.yyy.sys.rbac.service.DepartmentService;
import xxx.yyy.sys.rbac.service.PostService;
import xxx.yyy.sys.rbac.service.ResourceService;

import java.io.Serializable;
import java.util.List;

/**
 * Created by serv on 14-6-3.
 */
public abstract class AbstractContext implements Serializable,UserContext,RoleContext,SecretContext,PostContext,DepartmentContext,ResourceContext{

    private final static String IGNORE_DEPT_GROUP = DepartmentType.DEPARTMENT.getValue()+","+DepartmentType.GROUP.getValue();

    private final static String IGNORE_ORG_GROUP = DepartmentType.ORGANIZATION.getValue()+","+DepartmentType.GROUP.getValue();

    private final static String SYSTEM_SUPER_ADMIN_ROLE_NAME = "system";


    protected transient DepartmentService departmentService;
    protected transient AccountService accountService;
    protected transient ResourceService resourceService;
    protected transient PostService postService;

    public AbstractContext() {
        departmentService = SpringContextHolder.getBean(DepartmentService.class);
        accountService = SpringContextHolder.getBean(AccountService.class);
        resourceService = SpringContextHolder.getBean(ResourceService.class);
        postService = SpringContextHolder.getBean(PostService.class);
    }


    /**
     * 获取department 列表
     * @param deptId departmentId
     * @param containSelf 是否包含当前department
     * @param ignoreTypes 忽略的类型 , 多个以 , 逗号区分
     * @return list of {@link xxx.yyy.sys.rbac.model.Department}
     */
    private List<Department> getChildDepartments(String deptId,boolean containSelf,String ignoreTypes) {
        List<Department> departments = departmentService.getAllChildDepts(getUserOrgId(),containSelf, ignoreTypes);
        return departments;
    }



    @Override
    public String getUserOrgId() {
        return getUser().getOrgId();
    }

    @Override
    public Department getUserOrg() {
        return departmentService.findOne(this.getUserOrgId());
    }

    @Override
    public Department getUserRootOrg() {
        return departmentService.findRootDept(this.getUserOrgId());
    }

    @Override
    public String getUserRootOrgId() {
        return this.getUserRootOrg().getId();
    }

    @Override
    public Department getUserDefaultDept() {
        return departmentService.findOne(getUser().getDefaultDeptId());
    }

    @Override
    public String getUserDefaultDeptId() {
        return getUser().getDefaultDeptId();
    }

    @Override
    public List<Department> getUserChildOrgs(boolean containSelf) {
        return getChildDepartments(getUserOrgId(),containSelf,IGNORE_DEPT_GROUP);
    }

    @Override
    public List<String> getDepartmentIds() {
        return CollectionUtils.extractToList(this.getDepartmentList(),"id");
    }

    @Override
    public List<String> getAllChildDepartmentIds(boolean containSelf) {
        return CollectionUtils.extractToList(getAllChildDepartments(containSelf),"id");
    }

    @Override
    public List<Department> getAllChildDepartments(boolean containSelf) {
        List<Department> all = Lists.newArrayList();
        for(Department dept:getDepartmentList()){
            all.addAll(getChildDepartments(dept.getId(),containSelf,IGNORE_ORG_GROUP));
        }
        return all;
    }

    @Override
    public List<String> getUserChildOrgIds(boolean containSelf) {
        return CollectionUtils.extractToList(this.getUserChildOrgs(containSelf), "id") ;
    }

    @Override
    public List<String> getUserParentDeptIds(boolean containSelf) {
        return CollectionUtils.extractToList(this.getUserParentDepts(containSelf), "id") ;
    }

    @Override
    public List<Department> getUserParentDepts(boolean containSelf) {

        List<Department> allParents = Lists.newArrayList();

        for(Department department: getDepartmentList()){
            allParents.addAll(departmentService.getAllParent(department.getId(),containSelf,IGNORE_ORG_GROUP));
        }
        return allParents;
    }

    @Override
    public List<String> getUserGroupIds() {
        //TODO 获取用户的群组
        return null;
    }

    @Override
    public List<String> getPostIds() {
        return CollectionUtils.extractToList(getPostList(), "id") ;
    }

    @Override
    public boolean checkRole(String role) {
        return org.springframework.util.CollectionUtils.contains(this.geRoleNames().iterator(), role);
    }

    @Override
    public List<String> geRoleNames() {
        return CollectionUtils.extractToList(this.getRolesList(), "name");
    }

    @Override
    public List<String> getRoleIds() {
        return CollectionUtils.extractToList(this.getRolesList(), "id");
    }

    @Override
    public boolean isSystem() {
        return this.checkRole(SYSTEM_SUPER_ADMIN_ROLE_NAME);
    }
}
