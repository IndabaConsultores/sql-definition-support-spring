package es.indaba.sqld.test.spring.beans;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import es.indaba.sqld.annotations.spring.api.QueryRepository;

@Configuration
@ComponentScan(basePackages = {"es.indaba.sqld"})
@QueryRepository("es.indaba.sqld.test.sqld")
public class TestConfiguration {

}
