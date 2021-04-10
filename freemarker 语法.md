# freemarker教程

## 

## 判断语句

```html
    <#if count??>
        ${count}
    <#else >
        0
    </#if>
    // 为空 赋默认值
   ${imgUr?default('假如你为空值默认为我')}
  // 三元表达式
     ${(state==1)?string('1','0')}
```



## 遍历语句

```html
      <#if proList?? && (proList?size>0) >
            <#list proList as it >
                ${it_index} &nbsp;    ${it.title}  &nbsp;  ${it.name}  &nbsp;  ${it.price}  &nbsp;
            </#list>
        </#if>
```

## 变量

```html
        <#assign name="哈哈" />
        ${name}
```

## 文件引入

```html
        <#include '/copyright.ftl' />

```

