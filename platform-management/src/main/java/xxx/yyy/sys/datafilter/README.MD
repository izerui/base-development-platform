1. repository 和baseservice 增加方法 filter() 用来判断是否过滤
权限
2。 ConditionApplier 中控制 根据 filter 来判断是否添加 查询条
件
3。 查询条件是通过SystemContextHolder 获取的。包含规则权限 和
acl权限

4。 过滤：  如果当前entityClass 跟 规则中的 modelClassname 一
致  说明需要把当前的规则的 ql  附加给 查询条件 以and 连接

循环用户的规则列表（里面包含了角色的规则，当前用户的规则，当前部
门的规则，当前角色的规则） 把符合条件的规则 and 起来

5。 并且setParameters 把一些 变量设置进去包含本部门 ，上级部门
，子部门list，角色ids 等等



6。 一些特殊查询， 本部门 包含子部门等 做final 处理。

7。 附加规则，或者 delete的时候 附加规则， 需要覆盖到所有的方法


9. 加入acl 权限， 需要在 businessModel 中加入 listener 监听数据删除的状态。 然后对应删除掉acl中的数据

10。 condition 拼接数据条件的时候 要注意支持 以 order by 结尾的 condition参数 参考 applySorting（）

11. 如果condition 为left join  需要考虑分隔 condition jion 和 order by 中间去加入条件