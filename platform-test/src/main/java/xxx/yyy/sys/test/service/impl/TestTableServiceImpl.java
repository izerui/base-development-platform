package xxx.yyy.sys.test.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xxx.yyy.sys.base.service.BaseServiceImpl;
import xxx.yyy.sys.test.model.TestTable;
import xxx.yyy.sys.test.service.TestTableService;

/**
 * Created by serv on 2014/6/5.
 */
@Service
@Transactional(readOnly = true)
public class TestTableServiceImpl extends BaseServiceImpl<TestTable> implements TestTableService {
}
