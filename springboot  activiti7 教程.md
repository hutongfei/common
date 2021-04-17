# springboot  activiti7 教程

## 配置

```java
spring.datasource.url=jdbc:mysql://localhost:3306/activiti?useSSL=false&useUnicode=true&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=1234
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

#数据源指定 true  activiti会对数据库中所有表进行更新操作。如果表不存在，则自动创建。
spring.activiti.database-schema-update=true
#记录历史等级 可配置的历史级别有none, acitivity, audit, all
spring.activiti.history-level=audit
spring.activiti.db-history-used=true
```

## 依赖

```xml
   <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.activiti</groupId>
            <artifactId>activiti-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.76</version>
        </dependency>

```

## 基本操作

```java

/**
 * @author hutf
 * @createTime 2021年03月27日 11:55:00
 */
@Controller
public class ApiController {
    /**
     * 流程定义并部署
     * @return
     */
    @GetMapping("/deploy")
    @ResponseBody
    public String start() {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = engine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("holiday.bpmn").addClasspathResource("holiday.png").name("请假流程").key("holiday").deploy();

        StringBuffer sb = new StringBuffer();
        sb.append("流程Id " + deploy.getId());
        sb.append(" 流程名称" + deploy.getName());
        sb.append(" 流程Key " + deploy.getKey());

        return sb.toString();
    }

    /**
     * 新增 流程实例并启动
     * @return
     */
    @GetMapping("/startProcess")
    @ResponseBody
    public ProcessInstance startProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();//获取引擎对象
        RuntimeService runtimeService = processEngine.getRuntimeService();// 创建运行服务类

        Map<String, Object> map = new HashMap<>();
        map.put("assign0", "zhangsan");
        map.put("assign1", "lisi");
        map.put("assign2", "wangwu");

        ProcessInstance instance = runtimeService.startProcessInstanceByKey("holiday",map);// 创建实例



        System.out.println(" 流程定义Id " + instance.getProcessDefinitionId());//  流程定义Id holiday:1:6778f563-9f37-11eb-a149-6214b380ab75
        System.out.println("流程定义Key " + instance.getProcessDefinitionKey());
        System.out.println("实例名称name" + instance.getName());
        System.out.println("开始用户 " + instance.getStartUserId());
        return instance;
    }

    // 根据用户名 查询个人待办任务
    @GetMapping("/getTasks/{name}")
    @ResponseBody
    public JSONArray getTasks(@PathVariable String name ) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();//获取引擎对象
        TaskService taskService = processEngine.getTaskService();// 创建运行服务类
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .taskAssignee(name)
                .list();


        JSONArray array = new JSONArray();
        JSONObject object = null;
        for (Task task : taskList) {
            object  = new JSONObject();
            object.put("流程定义Key", task.getTaskDefinitionKey());
            object.put("流程实例ID", task.getProcessInstanceId());
            object.put("任务ID", task.getId());
            object.put("任务名称", task.getName());
            object.put("负责人", task.getAssignee());

            array.add(object);
        }
        return array;
    }

    // 根据任务ID，处理个人待办任务，
    @GetMapping("/fixedTask/{name}/{taskId}")
    @ResponseBody
    public String getTasks(@PathVariable String name, @PathVariable String taskId ) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();//获取引擎对象
        TaskService taskService = processEngine.getTaskService();// 创建运行服务类

        Task singleResult = taskService.createTaskQuery().taskAssignee(name).taskId(taskId).singleResult();
        if (singleResult != null) {
            taskService.addComment(singleResult.getId(),null,"我已处理");// 添加批注
            taskService.complete(singleResult.getId());
        }

        return "OK";
    }

    /**
     * 根据实例查询历史     处理任务列表
     * @param insId
     * @return
     */
    @GetMapping("/historyList/{insId}")
    @ResponseBody
    public JSONArray insId(@PathVariable String insId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();//获取引擎对象
        HistoryService historyService = processEngine.getHistoryService();// 创建运行服务类

        List<HistoricDetail> historicDetailList = historyService.createHistoricDetailQuery()
                .processInstanceId(insId).list();

        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(insId).list();
        JSONArray array = new JSONArray();
        JSONObject object = null;
        for (HistoricTaskInstance h : list) {
            System.out.println(h.getId());
            System.out.println(h.getProcessInstanceId());
            System.out.println(h.getName());
            System.out.println(h.getAssignee());
            System.out.println(h.getStartTime());
            System.out.println(h.getEndTime());

            object = new JSONObject();
            object.put("ID", h.getId());
            object.put("实例ID", h.getProcessInstanceId());
            object.put("节点名称", h.getName());
            object.put("处理人", h.getAssignee());
            object.put("开始时间", h.getStartTime());
            object.put("结束时间", h.getEndTime());

            array.add(object);
        }

        return array;
    }
```

## springsecurity 相关配置

```java


@Configuration
@EnableWebSecurity
public class DemoApplicationConfiguration extends WebSecurityConfigurerAdapter {
 
    private Logger logger = LoggerFactory.getLogger(BootActivitiApplication.class);
 
    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("123")
                .roles("admin")
                .and().withUser("zhangsan")
                .password("123")
                .roles("student")
                .and()
                .withUser("lisi")
                .password("123")
                .roles("teacher")
                .and()
                .withUser("wangwu")
                .password("123")
                .roles("supervisor");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
```

