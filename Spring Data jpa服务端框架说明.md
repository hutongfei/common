**Spring Data jpa服务端框架说明**

jdbcTemplate操作

1 更新一条数据

```java
update(String sql, @Nullable Object... args) 
```

```java
jdbcTemplate.update(sb.toString(), params.toArray());
```

其中params是List<String> 类型，转为object[] 类型

2 新增一条数据

​	其中id不能为空

```java
sb.append("INSERT INTO dm_documentInfo (doc_id, case_id, dm_order)";
sb.append("VALUES (?, ?, ?)";
jdbcTemplate.update(sb.toString(), o.getDocId(), o.getCaseId(), o.getDmOrder());
```

3 删除一条数据

```java
String sql = "DELETE FROM dept_car_use WHERE id = ? ";
jdbcTemplate.update(sql, id);
```

4 查询数据(分页)

```java
StringBuffer sb = new StringBuffer();
sb.append("select doc_type_id, org_id from doc_dm_template ");
sb.append("  where doc_type_id = ? and org_id = ? ");
return jdbcTemplate.query(sb.toString(), new String[]{docTypeId, orgId}, new ResultSetExtractor<Map<String, Object>>() {   
    @Override    
    public Map<String, Object> extractData(ResultSet rs){        
        if (rs.next()) {           
            Map<String, Object> map = new HashMap<String, Object>();            					map.put("doc_type_id", rs.getString("doc_type_id"));           				 			 map.put("org_id", rs.getString("org_id")); 
            map.put("doc_path", rs.getString("doc_path"));            					
            return map;
        }       
        return null;    
    }});
```

5一个列表不分页

```java
  public List<PaperQues> getListByPaperId(String paperId) {
        String sql = "select id,paper_id,ques_id,created_at from exam_paper_ques where paper_id=?";
        List<PaperQues> list = jdbcTemplate.query(sql, new Object[]{paperId}, new BeanPropertyRowMapper<>(PaperQues.class));
        return list;
    }
```

6查询单个字段

7查询多个