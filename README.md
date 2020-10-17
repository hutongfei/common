接口调用

RestTemplate  带header 的请求

postForEntity方法
（参数单个对象）
         RestTemplate restTemplate = new RestTemplate();
        // 2、使用postForEntity请求接口
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("id", UUID.randomUUID().toString());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,headers);
        ResponseEntity<String> response2 = restTemplate.postForEntity(thirdUrlPost, httpEntity, String.class);
        String body = response2.getBody();
        
（多个参数 列表集合）
  /**
     * list集合对象传值
     */
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
