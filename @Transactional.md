@Transactional

1 只能加到public 方法上面 

2，只会对 运行时异常 进行回滚（RuntimeException 或继承自 RuntimeException），

3，让Exception异常也进行回滚操作，在调用该方法前加上: @Transactional(rollbackFor = Exception.class) 