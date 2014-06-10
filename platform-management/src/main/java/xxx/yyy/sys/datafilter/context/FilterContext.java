package xxx.yyy.sys.datafilter.context;

import java.util.Collection;
import java.util.Map;

/**
 * Created by serv on 14-6-5.
 */
public interface FilterContext {

    /**
     * 获取用户具有的规则权限的jpql 列表
     *
     * @param modelClass     操作的entityClass
     * @param dataFilterType 数据过滤类型 {@link xxx.yyy.sys.datafilter.DataFilterType}
     * @param orgId 单位id
     * @return jpql 列表
     */
    public Collection<String> getFilterRuleJpqlList(final Class modelClass, final String dataFilterType,final String orgId);

    /**
     * 获取用户的变量map信息
     *
     * @return
     */
    public Map<String,Object> getFilterParameters();

}
