package es.indaba.sqld.test.spring.beans;

import org.springframework.stereotype.Component;

import es.indaba.sqld.annotations.spring.api.QueryResolver;

@Component
public class TestInjectionBean {

    @QueryResolver(name = "query1")
    private String query1;

    public String getQuery1() {
        return query1;
    }

    public void setQuery1(String query1) {
        this.query1 = query1;
    }

}
