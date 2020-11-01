自定义校验注解

批量校验List<对象> 集合

将List<对象> 封装成bean
   
    public class ThirdInfo {
        @NotBlank(message = "id 不能为空")
        private String id;
        @NotBlank(message = "name 不能为空")
        private String name;
        @DecimalMax(value = "100",message = "age 不能超过 100 ")
        private Integer age;
    }
    
    public class ThirdList {

    @Valid
    private List<ThirdInfo> dataList ;
    }

后台接收

     @PostMapping("/thirdApiPost3")
    public String thirdApi3(@RequestBody @Validated ThirdList thirdList, HttpServletRequest request) throws InterruptedException {
        System.out.println(thirdList);
        return "OK";
    }
    
发起请求
  
         RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> paramMap1 = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        paramMap.put("id", "");
        paramMap.put("name", "name-"+UUID.randomUUID().toString());
        paramMap.put("age", 10);
        mapList.add(paramMap);
        paramMap1.put("dataList",mapList );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE));

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(paramMap1,headers);
        ResponseEntity<String> response2 = null;
        try {
            response2 = restTemplate.postForEntity(thirdUrlPost3, httpEntity, String.class);
