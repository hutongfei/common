 @PostMapping("/getFilterList")
    public Map<String, Object> getFilterList(@RequestBody ProductDto productDto) throws SolrServerException, IOException {
        SolrQuery params = new SolrQuery();
        // 查询条件
        params.set("q", !StringUtils.isEmpty(productDto.getPname()) ? productDto.getPname() : "*:*");
        // 排序
        params.addSort(productDto.getSort() == 1 ? "price" : "id", SolrQuery.ORDER.desc);

        // 条件精准过滤
        StringBuffer condition = new StringBuffer();
        if (productDto.getMnPrice() != null && productDto.getMxPrice() == null)
            condition.append(" and price: [ " + productDto.getMnPrice() + " TO * ]");

        if (productDto.getMnPrice() == null && productDto.getMxPrice() != null)
            condition.append(" and price: [ * TO " + productDto.getMxPrice() + " ]");

        if (productDto.getMnPrice() != null && productDto.getMxPrice() != null)
            condition.append(" and price: [ " + productDto.getMnPrice() + " TO " + productDto.getMxPrice() + " ]");

        if (!StringUtils.isEmpty(productDto.getShopId())) {
            condition.append(" and shop_id : " + productDto.getShopId());
        }
        if (!StringUtils.isEmpty(productDto.getPname())) {
            condition.append(String.format(" and pname: * %s * ",productDto.getPname()));
        }
        if (!StringUtils.isEmpty(condition.toString())) {
            params.setQuery(condition.toString());
        }
        // 分页
        params.setStart(productDto.getStart());
        params.setRows(productDto.getRows());
        // 默认域
        params.set("df", "pname");
        // 只查询指定域
        params.set("fl", "id,pname,price,shop_id,tag");
//        // 开启高亮
//        params.setHighlight(true);
//        // 设置前缀
//        params.setHighlightSimplePre("<span style='color:red'>");
//        // 设置后缀
//        params.setHighlightSimplePost("</span>");
        // solr数据库是 product
        QueryResponse queryResponse = client.query("product", params);
        SolrDocumentList results = queryResponse.getResults();
        // 数量，分页用
        long total = results.getNumFound();// JS 使用 size=MXA 和 data.length 即可知道长度了（但不合理）
        // 获取高亮显示的结果, 高亮显示的结果和查询结果是分开放的
//        Map<String, Map<String, List<String>>> highlight = queryResponse.getHighlighting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("data", results);
        return map;
    }
  
  
  solr 解决中文乱码问题
  	url="jdbc:mysql://127.0.0.1:3306/my-db?useSSL=false&amp;characterEncoding=utf-8&amp;serverTimezone=Asia/Shanghai"

  
