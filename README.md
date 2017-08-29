# sql-definition-support-spring
Proposal for an Spring integration of the base SQL query externalization library sql-definition-support

## First approach

```java
@Configuration
@ComponentScan(basePackages = {"es.indaba.sqld"})
@QueryRepository("es.indaba.sqld.test")
public class TestConfiguration {

}
```

```java
@Component
public class TestInjectionBean {

    @QueryResolver(name = "query1")
    private String query1;
...
```

file in class path es/indaba/sqld/test/yaml/ test.ysqld

```yaml
query1: |
 QUERY1_CONTENT
query2: |
 QUERY2_CONTENT
query5: |
 Select * 
 from table
 where a=1
 and b=?
```
