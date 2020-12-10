## 										ftl 页面静态化



```java
    @RequestMapping(value="/{id}/details.html", produces="text/html")
    @ResponseBody
    public String details(@PathVariable String id, Model model) throws Exception {
        return psp.ftlToHtml();
    }
    
```





```java

@Service
public class PageServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageServiceImpl.class) ;
    private static final String PATH = "/templates/" ;

    public String ftlToHtml() throws Exception {
        // 创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 设置模板路径
        String classpath = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(classpath + PATH));
        // 加载模板
        Template template = configuration.getTemplate("details.ftl");
        // 数据模型
        Map<String, Object> map = new HashMap<>();
        map.put("myTitle", "页面静态化(PageStatic)");
        map.put("tableList",getList()) ;
        // 静态化页面内容
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        LOGGER.info("content:{}",content);
//        InputStream inputStream = IOUtils.toInputStream(content,"UTF-8");
//        // 输出文件
//        FileOutputStream fileOutputStream = new FileOutputStream(new File("F:/page/newPage.html"));
//        IOUtils.copy(inputStream, fileOutputStream);
//        // 关闭流
//        inputStream.close();
//        fileOutputStream.close();
        return content;
    }

    private List<Map<String, Object>> getList (){
        List<Map<String, Object>> tableInfoList = new ArrayList<>() ;
        Map<String, Object> map = new HashMap<>();
        map.put("id", UUID.randomUUID().toString());
        map.put("name", "张三");
        tableInfoList.add(map);
        return tableInfoList ;
    }

```



```html
<html>
<head>
    <title>PageStatic</title>
</head>
<body>
<table  id="editable-sample">
    <thead>
    <tr>
        <th>规格描述</th>
        <th>产品详情</th>
    </tr>
    </thead>
    <tbody>
    <#list tableList as info>
        <tr class="">
            <td>${info.id}</td>
            <td>${info.name}</td>
            <td><button onclick="btn()">点击</button></td>
        </tr>
    </#list>
    </tbody>
</table><br/>
<script>
    function btn() {
        alert(1)
    }
</script>
</body>
</html>
	
```

