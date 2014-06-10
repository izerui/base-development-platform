package xxx.yyy.sys.datafilter.repository;

import org.springframework.stereotype.Repository;
import xxx.yyy.sys.base.jpa.PlatformJpaRepository;
import xxx.yyy.sys.datafilter.model.FilterRule;

/**
 * Created by serv on 14-6-4.
 */
@Repository
public interface FilterRuleRepository extends PlatformJpaRepository<FilterRule>{
}
