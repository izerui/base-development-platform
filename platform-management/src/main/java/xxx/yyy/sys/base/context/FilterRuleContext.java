package xxx.yyy.sys.base.context;

import xxx.yyy.sys.datafilter.model.FilterRule;

import java.util.List;

/**
 * Created by serv on 14-6-4.
 */
public interface FilterRuleContext {

    /**
     * 获取用户具有的规则权限列表
     * @return
     */
    public List<FilterRule> getFilterRuleList();

}
