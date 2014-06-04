package xxx.yyy.sys.datafilter.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xxx.yyy.sys.base.service.BaseServiceImpl;
import xxx.yyy.sys.datafilter.model.FilterRule;
import xxx.yyy.sys.datafilter.service.FilterRuleService;

/**
 * Created by serv on 14-6-4.
 */
@Service
@Transactional(readOnly = true)
public class FilterRuleServiceImpl extends BaseServiceImpl<FilterRule> implements FilterRuleService {
}
