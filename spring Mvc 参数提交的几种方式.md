## spring Mvc 提交的几种方式



```java
   private String id;

    private String name;

    private Integer sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birth;
```



### 方式一 json 方式（通过json 格式放入方法体中提交）

####  单个对象

```java
    @PostMapping("/cmtPerson1")
    public Person postPerson1(@RequestBody Person person) {
        System.out.println(person.toString());
        return person;
    }
```



提交方式

```json
{
  "id": "",
  "name": "",
  "sex": 0,
  "birth": "2021-04-01 16:43:25"
}
```

#### 多个对象

```java
    @PostMapping("/postPersonList1")
    public  List<Person> postPersonList1(@RequestBody List<Person> list) {
        System.out.println(list);
        return list;
    }
```



httpClient 调用

```java
     String url = "http://localhost:8001/cmtPerson1";
        String json = "{\n" +
                "  \"id\": \"11111\",\n" +
                "  \"name\": \"2222\",\n" +
                "  \"sex\": 0,\n" +
                "  \"birth\": \"2021-04-01 16:43:25\"\n" +
                "}";
        Map<String, Object> map = new HashMap<>();
        map.put("id", UUID.randomUUID().toString());
        map.put("name", "张三");
        map.put("sex", 1);
        map.put("birth", "2020-10-10 10:12:12");
        
        String result = "";
        String path = url;
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(path);
        //添加参数
        HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        try {
            HttpResponse response = client.execute(post);
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(result);
```



### 方式二  表单方式

#### 单个对象

```java
    @PostMapping("/cmtPerson2")
    public Person postPerson2(Person person) {
        System.out.println(person.toString());
        return person;
    }
```

提交方式通过form 表单方式提交

对于集合的提交方式（普通表单提交 ） getIds：[1,3,5,aa]

```java
   @RequestMapping("/getIds")
    public String getIds(@RequestParam String[] getIds) {
        System.out.println(getIds);
        return getIds.toString();
    }
```

