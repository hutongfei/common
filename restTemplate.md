接口调用

RestTemplate  带header 的请求

传参方案 https://zhuanlan.zhihu.com/p/89223401

postForEntity方法
（参数单个对象）（接口用 Map<String,Object> 接收）

        RestTemplate restTemplate = new RestTemplate();
        // 2、使用postForEntity请求接口
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("id", UUID.randomUUID().toString());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,headers);
        ResponseEntity<String> response2 = restTemplate.postForEntity(thirdUrlPost, httpEntity, String.class);
        String body = response2.getBody();
        
        
 单个对象传参（后台用@RequestBody List<ThirdInfo> params 接收） （Map<String,Object> 传参）
        Map<String, Object> paramMap = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();

        paramMap.put("id", "1");
        paramMap.put("name", "name-"+UUID.randomUUID().toString());
        paramMap.put("age", 10);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE));
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(paramMap,headers);

        ResponseEntity<String> response2 = restTemplate.postForEntity(thirdUrlPost3, httpEntity, String.class);
        String body = response2.getBody();
        
 对象集合传参（接口用 Map<String,Object> 接收）
         
         public Object postMethodManyParam() {
        HashMap<String, Object> map = null;
        List<Map<String, Object>> listParam = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            map = new HashMap<>();
            map.put("id", UUID.randomUUID());
            map.put("name", "System.name " + i);
            listParam.add(map);
        }
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("dataList", listParam);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap, headers);
        ResponseEntity<String> response2 = restTemplate.postForEntity(thirdUrlPostManyParam, httpEntity, String.class);
        String body = response2.getBody();

        return body;
    }

（对象集合传参）（后台用@RequestBody List<ThirdInfo> params 接收）（List<Map<String,Object>> 传参）
        
        Map<String, Object> paramMap = null;
        ArrayList<Map<String, Object>> mapList = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE));
        HttpEntity<ArrayList<Map<String, Object>>> httpEntity = new HttpEntity<ArrayList<Map<String, Object>>>(mapList,headers);
        ResponseEntity<String> response2 = restTemplate.postForEntity(thirdUrlPost2, httpEntity, String.class);
        String body = response2.getBody();
        
注意：如果后台不是用 @requestBody 接收，必须要resttemplate postForEntity 必须要 MultiValueMap结构传参
